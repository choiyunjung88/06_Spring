package edu.kh.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller //요청/응답 제어 역할 명시 + bean emdfhr
public class MainController {

	@RequestMapping("/")
	public String mainPage() {
		
		
		return "common/main";
	}
			
}
