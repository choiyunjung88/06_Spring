package edu.kh.project.myPage.model.mapper;

import edu.kh.project.member.model.dto.Member;

public interface MyPageMapper {

	int updateInfo(Member inputMember);
	String selectPw(int memberNo);
}
