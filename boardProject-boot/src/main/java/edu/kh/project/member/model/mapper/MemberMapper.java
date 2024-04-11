package edu.kh.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	
	/**
	 * @param memberEmail
	 * @return loginMember
	 */
	Member login(String memberEmail);
	
	int checkEmail(String memberEmail);
}
