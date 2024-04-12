package edu.kh.project.email.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.project.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("email")
@RequiredArgsConstructor // final 필드/ @notnull필드에 자동으로 의존성 주입
public class EmailController {
	@Autowired
	private EmailService service;
	@PostMapping("signup")
	@ResponseBody
	public int signup(@RequestBody String email) {
		String authKey = service.sendEmail("signup", email);
		if(authKey != null) {// 인증번호가 반환되서 돌아옴
			// 이메일 보내기 성공
			return 1;
		}
		//이메일 보내기 실패
		return 0;
		
	}
	
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String,Object> map) {
		return service.checkAuthkey(map);
	}
	
	

	
	
	
	
	
	/*
	 * @Autowirde를 이용한 의존성 주입 반법은 3가지 존재
	 * 1)필드
	 * 2.setter
	 * 3.생성자 (권장)
	 * 
	 * setter 이용
	 * 필드 중 초기화되지 않은 final이 붙은 필드
	 * 초기화 되지 않은 @notnull이 붙은 필드
	 * 1, 2
	 * 
	 * 
	 * */
}











