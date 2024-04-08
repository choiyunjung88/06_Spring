package edu.kh.todo.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.mapper.TodoMapper;

@Service
public class ToDoServiceImpl implements TodoService{

	@Autowired
	private TodoMapper mapper;

	// 할일 목록 + 완료된 할일 개수 조회
	@Override
	public Map<String, Object> selectAll() {
		// 1. 할 일 목록 조회
		List<Todo> todoList = mapper.selectAll();
		// 2. 완료된 할일 개수 조회
		
		// Map으로 묶어서 반환
		return null;
	}
	
	@Override
	public int addTodo(String todoTitle, String todoContent) {
		// 마이바티스에서 SQL에 전달할 수 있는 파라미터의 개수는
		// 오직 1개
		// -> todoTitle, todoContent를 Todo DTO로 묶어서 전달
		Todo todo = new Todo();
		todo.setTodoTitle(todoTitle);
		todo.setTodoContent(todoContent);
		
		return mapper.addTodo(todo);
	}

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Todo todoDetail(int todoNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int todoDelete(int todoNo) {
		// TODO Auto-generated method stub
		return 0;
	}
}
