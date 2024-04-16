package edu.kh.project.myPage.model.service;

import java.io.File;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

	private final MyPageMapper mapper;
	//BCrypt 암호화 객체 의존성 주입
	private final BCryptPasswordEncoder bcrypt;
	
	@Override
	public int changePw(Map<String, Object> paramMap, int memberNo) {
		String originPw = mapper.selectPw(memberNo);
		if(!bcrypt.matches((String)paramMap.get("currentPw"), originPw)) {
			return 0;
		}
		String encPw = bcrypt.encode((String)paramMap.get("newPw"));
		paramMap.put("encPw", encPw);
		paramMap.put("memberNo", memberNo);
		return mapper.changePw(paramMap);
	}
	
	
	
	
	
	
	
	
	
	// 회원 정보 수정
	@Override
	public int updateInfo(Member inputMember, String[] memberAddress) {
		if(inputMember.getMemberAddress().equals(",,")) {
			inputMember.setMemberAddress(null);
		}else {
			String address = String.join("^^^", memberAddress);
			inputMember.setMemberAddress(address);
		}
		return mapper.updateInfo(inputMember);
	}
	
	
	
	
	
	
	
	
	@Override
	public int secession(String memberPw, int memberNo) {
		String originPw = mapper.selectPw(memberNo);
		
		if(!bcrypt.matches(memberPw, originPw)) {
			return 0;
		}
		
	
		
		
		
		
		
		
		return mapper.secession(memberNo);
	}









	@Override
	public String fileUpload1(MultipartFile uploadFile) throws Exception{

		//MultipartFile가 제공하는 메서드
		// -getSize() : 파일 크기
		// -isEmpty() : 업로드한 파일이 없을 경우 true
		// -getOriginalFileName(): 원본 파일 명
		// -transferTo(경로) :
		// 메모리 또는 임시저장 경로에 업로드 된 파일을
		// 원하는 경로에 전송(서버 어떤 폴더에 저장할지 지정)
		if(uploadFile.isEmpty()) {
			return null;
		}
		// 업로드한 파일이 있을 경우
		// C:/uploadFiles/text/파일명 으로 서버에 저장
		uploadFile.transferTo(
				   new File("C:\\uploadFiles\\test\\" + uploadFile.getOriginalFilename())
				);
		// 웹에서 해당 파일에 접근할 수 있는 경로를 반환
		// 서버 : C:\\uploadFiles\\test\\a.jpg
		// 웹접근주소 : /myPage/file/a.jpg
		return "/myPage/file/" + uploadFile.getOriginalFilename();
	}
}
