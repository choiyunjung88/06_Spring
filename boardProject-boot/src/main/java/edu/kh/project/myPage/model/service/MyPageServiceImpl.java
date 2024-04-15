package edu.kh.project.myPage.model.service;

import java.util.Map;

public class MyPageServiceImpl {

	@Override
	public int changePw(Map<String, Object> paramMap, int memberNo) {
		String originPw = mapper.selectPw(memberNo);
		if(!bcrypt.matches((String)paramMap.get("currentPw"), originPw))
	}
}
