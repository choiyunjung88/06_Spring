package edu.kh.todo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.ToDoServiceImpl;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller //요청/응답 제어 역할 명시 + bean emdfhr
public class MainController {
	@Autowired
	private TodoService service;

	@RequestMapping("/")
	public String mainPage() {
		
		//의존성 주입 확인
		log.debug("service : " + service);
		
		//Service 메서드 호출 후 결과 반환 받기
		Map<String, Object> map = service.selectAll();
		
		//MAP에 담긴 내용 추출
		List<Todo> todoList = (List<Todo>)map.get("todoList");
		int completeCount = (int)map.get("completeCount");
		Model.addAttribute("todoList", todoList)
		Model.addAttribute("completeCount", completeCount);
		return "common/main";
	}
			
}
