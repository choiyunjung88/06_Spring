package edu.kh.todo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class Todo {

	private int todoNo;        //할일번호
	private String todoTitle;  //할일제목
	private String todoContent;//할일내용
	private String complete;   //할일완료여부
	private String regDate;    //할일등록일(String으로 변환)
}
