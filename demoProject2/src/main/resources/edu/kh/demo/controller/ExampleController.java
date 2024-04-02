package edu.kh.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.demo.model.dto.Student;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller //요청/응답 제어 역할 명시 + Bean등록
@RequestMapping("example")
@Slf4j // lombok 라이브러리가 제공하는ㄴ lomgeck  
public class ExampleController {
	
	/*
	 * Model
	 * -Spring 에서 데이터 전달 역할을 하는 객체
	 * -org.springframwork.ui 패키지
	 * -기본 : scope : request
	 * -@SessionAttributes와 함께 사용시 session
	 * 
	 * 
	 * Model.addAttribute("key", value);
	 * */
	
	
	
	
	@GetMapping("ex1")
	public String ex1(HttpServletRequest req, Model model) {
		
		
		
		//내장객체 범위
		// page < request < session < appliciaon
		
		// request scpe
		req.setAttribute("test1", "HttpServletRequest로 전달한 값");
		model.addAttribute("test2", "Model로 전달한 값");
		
		
		
		
		// 단일 값(숫자, 문자열) Model을 이용해서 html로 전달
		model.addAttribute("productName", "종이컵");
		model.addAttribute("price", 2000);
		
		
		
		
		// 복수 값(배열, List) Model을 이용해서 html로 전달
		List<String> fruitList = new ArrayList<>();
		fruitList.add("사과");
		fruitList.add("딸기");
		fruitList.add("바나나");
		
		model.addAttribute("fruitList", fruitList);
		
		
		// DTO 객체 Model을 이용해서 html로 전달
		Student std = new Student();
		std.setStudentNo("12345");
		std.setName("홍길동");
		std.setAge(22);
		
		model.addAttribute("std", std);
		
		//List<Student> 객체를 이용해서 ㅇhtmlㅗ들어와
		List<Student> stdList = new ArrayList();

		
		return "example/ex1"; // templates/example/ex1.html 요청 위임
	}
	
	
	
	
	@PostMapping("ex2")  // /example/ex2 Post 방식 매핑
	public String ex2(Model model) {
		
		model.addAttribute("str", "<h1>테스트중 &times; </h1>");
		
		
		
		return "example/ex2";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
