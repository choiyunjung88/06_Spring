package edu.kh.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;


/*
 * @ResponseBody
 * 
 * [Httpmessagelonverter]
 * Spring에서 비동기 통신 시
 * - 전달되는 데이터의 자료형
 * - 응딥하는 데이터의 자료형
 * 위 두가지를 알맞은 형태로 가공 해주는 객체
 * 
 * -문자열, 숫자 <-> text
 * - dtd <-> json
 * - map <-> json
 * httpmessageconverter가 동작하기 위해서는
 * jackson-data-bind 라이브러리가 필요한데 spring boot 모듈에 내장되어 있음
 * */

@Slf4j
@RequestMapping("ajax")
@Controller // 요청/응답 제어 역할 명시 + Bean 등록
public class AjaxController {
	
	// 등록된 bean중 같은 타입 또는 상속관계인 bean을 해당 필드에 의존성 주입
	@Autowired
	private TodoService service;
	
	@GetMapping("main") // /ajax/main Get 요청 매핑
	public String ajaxMain() {
		
		// 접두사 : classpath:templates/
		// 접미사 : .html
		return "ajax/main";
	}
	// 전체 todo 개수 조회
	public int getTotalCount() {
		//전체 할일 개수 조회 서비스 호출 및 응답
		int Toservice.getTotalCount();
	return service.addTodo(null, null);
	}
	
	@GetMapping
	public List<Todo> selectList(){
		return null;
		
	}
	
	
	public Todo selectTodo(@RequestParam("todoNo") int todoNo) {
		// return 자료형 : Todo
		return service.todoDetail(todoNo);
	}
	
	// Delete 방식 요청 처리 (비동기 요청만 가능!!)
	@ResponseBody
	@DeleteMapping("delete")
	public int todoDelete(@RequestBody int todoNo) {
		return service.todoDelete(todoNo);
	}
	
	
	
	
	
	
	
	
}
