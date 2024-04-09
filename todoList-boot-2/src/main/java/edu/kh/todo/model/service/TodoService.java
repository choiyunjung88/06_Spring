package edu.kh.todo.model.service;

import java.util.Map;

import edu.kh.todo.model.dto.Todo;

/**
 * 할일 목록 + 완료된 할 일 개수 조회
 * @return map
 */
public interface TodoService {

	Map<String, Object> selectAll();

	/** 할일 추가
	 * @param todoTitle
	 * @param todoContent
	 * @return result
	 */
	int addTodo(String todoTitle, String todoContent);

	int getTotalCount();

	Todo todoDetail(int todoNo);

	int todoDelete(int todoNo);

	int changeComplete(Todo todo);
}
