package edu.kh.project.email.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.project.email.model.mapper.EmailMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional // 예외 발생하면 롤백할게 (기본값 커밋)
@Service
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender mailSender;
	@Autowired
	private EmailMapper mapper;
	//타임리프를 이용해서 html코드 -> java로 변환
	private final SpringTemplateEngine templateEngine;

	@Override
	public String sendEmail(String htmlName, String email) {
		String authKey = createAuthKey();
		
		try {
			String subject = null;
			switch(htmlName) {
			case "signup" :
				subject = " [boardProject] 회원가입 인증번호 입니다."; break;
			}
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(loadHtml(authKey, htmlName), true);
			// CID 를 이용해 메일에 이미지 첨부
			// logo 추가 예정
			//helper.addInline("logo", new );
			// 메일 보내기
			mailSender.send(mimeMessage);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		// 이메일, 인증번호를 "TB_AUTHKEY"테이블 저장
		Map<String, String> map = new HashMap<>();
		map.put("authKey", authKey);
		map.put("email", email);
		
		// 1) 해당 이메일이 DB에 존재하는 경우가 있을 수 있기 때문에
		//    수정(update)를 먼저 진행
		//    -> 1 반환 == 업데이트 성공 == 이미 존재해서 인증번호 변경
		//    -> 0 반환 == 업데이트 실패 == 이메일 존재 X --> INSERT 시도
		
		int result = mapper.updateAuthKey(map);
		// 2) 1번 업데이트 실패시 insert를 시도해라
		if( result == 0 ) {
			result = mapper.insertAuthKey(map);
		}
		
		// 수정, 삽입 후에도 result가 0 == 실패
		if(result == 0) return null;
		
		// 성공
		return authKey; // 오류없이 완료되면 authKey 반환

	}
	
	
	
	
	
	
	
	
	
	
	
	private String loadHtml(String authKey, String htmlName) {
	
		//org.thymeleaf.Context 선택
		Context context = new Context();
		
		// 타임리프가 적용된 html에서 사용할 값 추가
		context.setVariable("authKey", authKey);
		
		// templates/email 폴더에서 htmlName과 같은
		// -.html 파일 내용을 읽어와 String으로 변환
		return templateEngine.process("email/" + htmlName,  context);
			
	}

	/** 인증번호 생성 (영어 대문자 + 소문자 + 숫자 6자리)
	    * @return authKey
	    */
	   public String createAuthKey() {
	   	String key = "";
	       for(int i=0 ; i< 6 ; i++) {
	          
	           int sel1 = (int)(Math.random() * 3); // 0:숫자 / 1,2:영어
	          
	           if(sel1 == 0) {
	              
	               int num = (int)(Math.random() * 10); // 0~9
	               key += num;
	              
	           }else {
	              
	               char ch = (char)(Math.random() * 26 + 65); // A~Z
	              
	               int sel2 = (int)(Math.random() * 2); // 0:소문자 / 1:대문자
	              
	               if(sel2 == 0) {
	                   ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
	               }
	              
	               key += ch;
	           }
	          
	       }
	       return key;
	   }








	@Override
	public int checkAuthkey(Map<String, Object> map) {
		return mapper.checkAuthKey(map);
	}

}
