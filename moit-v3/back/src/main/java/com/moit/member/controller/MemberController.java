package com.moit.member.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
	
	@Value("${app.frontend-url}")
	private String frontendUrl;
	
    // 카카오 로그아웃 완료 후 진입
    @GetMapping("/user/member/kakaologout")
    public String kakaoLogout() {

        return "redirect:"+ frontendUrl +"/user/member/login";
    }
}