package edu.kh.project.myPage.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.RequestBody;



@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService service;
	
	/**내정보 조회/수정 화면으로 전환
	 * 
	 * @param loginMember : 세션에 존재하는 loginMember를 얻어와 매개변수에 대입
	 * @return
	 */
	@GetMapping("info")
	public String info(@SessionAttribute("loginMember") Member loginMember,
					   Model model) {
		
		//주소만 꺼내용
		
		String memberAddress = loginMember.getMemberAddress();
		
		// 주소가 있을 경우에만 동작
		if(memberAddress != null) {
			// 구분자 "^^^"를 기준으로
			// memberAddress 값을 쪼개어 String[]로 반환
			String[] arr = memberAddress.split("\\^\\^\\^");
			model.addAttribute("postcode", arr[0]);
			model.addAttribute("address", arr[1]);
			model.addAttribute("detailAddress", arr[2]);
		}
		return "myPage/myPage-info";
	}
	
	/**프로필 이미지변경 화면 이동
	 * @return
	 */
	@GetMapping("profile")
	public String profile() {
		return "myPage/myPage-profile";
	}

	
	
	
	/**비밀번호 변경 화면 이동
	 * @param paramMap
	 * @param loginMember
	 * @return
	 */
	@GetMapping("changePw")
	public String changPw(@RequestParam Map<String, Object> paramMap,
			@SessionAttribute("loginMember") Member loginMember
			
			) {
		 return "myPage/myPage-changePw";
	}
	
	/**회원 탈퇴 화면 이동
	 * @return
	 */
	@GetMapping("secession")
	public String secession() {
		return "myPage/myPage-secession";
	}
	
	/** 회원 정보 수정
	 * @param inputMember
	 * @param loginMember
	 * @param memberAddress
	 * @param ra
	 * @return
	 */
	@PostMapping("info")
	public String updateInfo(Member inputMember,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("memberAddress") String[] memberAddress,
			RedirectAttributes ra) {
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
		
		
		
		int result = service.updateInfo(inputMember, memberAddress);
		
		String message = null;
		if(result >0) {
			message = "회원 정보 수정 성공";
			
			loginMember.setMemberNickname(inputMember.getMemberNickname());
			loginMember.setMemberTel(inputMember.getMemberTel());
			loginMember.setMemberAddress(inputMember.getMemberAddress());
		}else {
			message = "회원 정보 수정 실패";
			
			
		}
		ra.addFlashAttribute("message", message);
		
		return "redirect:info";
	}
	
	/**비밀번호 변경
	 * @param paramMap
	 * @param loginMember
	 * @param ra
	 * @return
	 */
	@PostMapping("changePw")
	public String changePw(@RequestParam Map<String, Object> paramMap,
							@SessionAttribute("loginMember") Member loginMember,
							RedirectAttributes ra
			) {

		int memberNo = loginMember.getMemberNo();
		int result = service.changePw(paramMap, memberNo);
		String path = null;
		String message = null;
		if(result >0) {
			path = "/myPage/info";
			message = "비밀번호가 변경되었습니다.";
			
		}else {
			path = "/myPage/changPw";
			message = "현재 비밀번호가 일치하지 않습니다";
		}
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	/** 회원 탈퇴
	 * @param memberPw
	 * @param loginMember
	 * @param status
	 * @param ra
	 * @return
	 */
	@PostMapping("secession")
	public String secession(
							@RequestParam("memberPw") String memberPw,
							@SessionAttribute("loginMember") Member loginMember,
							SessionStatus status,
							RedirectAttributes ra
			) {
		int memberNo = loginMember.getMemberNo();
		int result = service.secession(memberPw, memberNo);
		
		
		String message = null;
		String path = null;
		if(result > 0) {
			message = "탈퇴되었습니다";
			path="/";
			status.setComplete();
		}else {
			message = "비밀번호가 일치하지 않습니다";
			path = "secession";
		}
		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
		
	}
	
	
	
	// ----------------------------------------------------------
	// 파일 업로드 테스트
	
	@GetMapping("fileTest")
	public String fileTest() {
		return "myPage/myPage-fileTest";
	}
	
	/* Spring에서 파일 업로드를 처리 하는 방법
	 * - enctype="multipar/rng
	 * 
	 * 
	 * 
	 * */
	@PostMapping("file/test1")
	public String fileUpload(/*@RequestParam("memberName") String memberName,*/
	@RequestParam("uploadFile") MultipartFile uploadFile, RedirectAttributes ra
	)throws Exception{
		String path = service.fileUpload1(uploadFile);
		//파일이 저장되어 웹에서 접근할 수 있는 경로가 반환 되었을 때
		if(path != null) {
			ra.addFlashAttribute("path", path);
		}
		return "redirect:/myPage/fileTest";
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
