package edu.kh.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;




/* @SessionAttributes({"key", "key" ...})
 * - Model에 추가된 속성 중
 * 	 key값이 일치하는 속성을 session scope로 변경
 * 
 * */

@SessionAttributes({"loginMember"})
@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {

	@Autowired // 의존성 주입(DI)
	private MemberService service;
	
	/*   [로그인]
	 * - 특정 사이트에 아이디/비밀번호 등을 입력해서
	 *   해당 정보가 있으면 조회/서비스 이용
	 * - 로그인 한 정보를 session에 기록하여
	 *   로그아웃 또는 브라우저 종료 시까지
	 *   해당 정보를 계속 이용할 수 있게 함
	 * 
	 * */
	
	/*
	 * param inputMember : 커맨드 객체 (@ModelAttribute 생략)
	 *                     (memberEmail, memberPw 세팅된 상태)
	 * param ra : 리다이렉트 시 request scope로 데이터를 전달하는 객체
	 * */
	@PostMapping("login")
	public String login(Member inputMember,
			            RedirectAttributes ra,
			            Model model,
			            @RequestParam(value="saveId", required= false) String saveId,
			            HttpServletResponse resp) {
		Member loginMember = service.login(inputMember);
		if(loginMember == null) {
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
		}
		
		// 로그인 성공 시
		if(loginMember != null) {
			
			// Session scope에 loginMember추가
			model.addAttribute("loginMember", loginMember);
			// 1단계 : request scope 에 세팅됨
			// 2단계 : 클래스 위에 @SessionAttributes() 어노테이션 때문에
			//         session scope로 이동됨
			
			//------------------------------------------------------------------
			
			// 아이디 저장(Cookie)
			// 쿠키 객체 생성(K:V)
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
			// savedId=user01@kh.or.kr
			//클라이언트가 어떤 요청을 할 때 쿠키가 첨부될지 지정
			// ex) "/" : IP 또는 도메인 또는 localhost
			//           뒤에 "/" --> 메인페이지 
			cookie.setPath("/");
			if(saveId != null) {
				// 아이디 저장 체크 시
				cookie.setMaxAge(60 * 60 *24*30); //30일 (초단위로 지정)
			}else {
				// 미체크 시
				cookie.setMaxAge(0); //0초 (클라이언트 쿠키 삭제)
				resp.addCookie(cookie);
			}
		}
		
		return "redirect:/"; //메인페이지 재요청
		
	}
	/** 로그아웃 : Session에 저장된 로그인된 회원 정보를 없앰(만료, 무효화)
	 * @param SessionStatus : 세션을 완료시키는 역할의  객체
	 * 		  @SessionAttributes 로 등록된 세션을 만료
	 * 		  서버에서 기존 세션 객체가 사라짐과 동시에
	 *		  새로운 세션 객체가 생성되어 클라이언트와 연결
	 * @return
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		status.setComplete(); //세션을 완료시킴
		return "redirect:/"; //메인페이지 리다이랙트
		
	}
	
	/** 회원가입 페이지 이동
	 * @return
	 */
	@GetMapping("signup")
	public String signupPage() {
		return "member/signup";
	}
	
	@ResponseBody
	@GetMapping("checkEmail")
	public int checkEmail(@RequestParam("memberEmail") String memberEmail) {
		return service.checkEmail(memberEmail);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
