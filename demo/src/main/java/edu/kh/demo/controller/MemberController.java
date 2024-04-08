package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.kh.demo.model.dto.Member;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberController {
	@PostMapping("member/select")
	public String membermethod(HttpServletRequest req, @ModelAttribute Member member) {
		req.setAttribute("memberName", member.getMemberName());
		req.setAttribute("memberAge", member.getMemberAge());
		req.setAttribute("memberAddress", member.getMemberAddress());
		
		return "/member/select";
		
	}
}
