package edu.kh.demo.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {
	private String memberName;
	private int memberAge;
	private String memberAddress;
}
