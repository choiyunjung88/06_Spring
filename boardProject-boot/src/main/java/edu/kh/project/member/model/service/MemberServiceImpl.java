package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(rollbackFor=Exception.class) // 해당 클래스 메서드 종료 시 까지
				// - 예외(RuntimeException)가 발생하지 않으면 commit
				//// - 예외(RuntimeException)가 발생하면 rollback
@Service // 비즈니스 로직 처리 역할 명시 + Bean 등록
public class MemberServiceImpl implements MemberService {

	// 등록된 bean 중에서 같은 타입 또는 상속관계인 bean을
	// 자동으로 의존성 주입(DI)
	@Autowired
	private MemberMapper mapper;

	//BCrypt 암호화 객체 의존성 주입
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	
	// 로그인 서비스
	@Override
	public Member login(Member inputMember) {
		
		//테스트        평문비번
		// bcrypt.encode(문자열) : 문자열을 암호화하여 반환
		//String bcryptPassword = bcrypt.encode(inputMember.getMemberPw());
		//log.debug("bcryptPassword : "+ bcryptPassword);
		
		
		//boolean result = bcrypt.matches(inputMember.getMemberPw(), bcryptPassword);
		//log.debug("result : " + result);
		
		
		
		
		// 1. 이메일이 일치하면서 탈퇴하지 않은 회원 조회
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		
		// 2. 만약에 이ㅐㄹ치하는 이메일이 없어서 조회 결과가 NULL인 경우
		if(loginMember == null) return null;
		
		// 3. 입력받은 비밀번호(inputMember.getMemberPw() 평문)와
		// 암호화된 비밀번호(loginMember.getMemberPw())
		// 두 비밀번호가 일치하는지 확인
		
		// 일치하지 않으면 
		if( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}
		
		
		//로그인 결과에서 비밀번호 제거
		loginMember.setMemberPw(null);
		
		
		// memberEmail: user01@kh.or.kr
		// memberPw: pass01!
		return loginMember;
	}
	
	
	
	/* BCrypt 암호화 (Spring Security 제공)
	 * 
	 * - 입력된 문자열(비밀번호)에 salt를 추가한 후 암호화
	 * 
	 * ex) A 회원 : 1234     ->     $12!asdfg
	 *     B 회원   1234     ->     $12!dfhfd
	 * - 비밀번호 확인 방법
	 * -> BCryptPasswordEncoder.matches(평문 비밀번호, 암호화된 비밀번호)
	 * -> 평문 비밀번호와 암호화된 비밀번호가  
	 * 
	 * 
	 * sha 방식 암호화 (참고사항)
	 * - 암호화 시 변경된 내용이 같음
	 * */
	
	
	
	
	
	
}
