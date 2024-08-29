package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.SignupRequest;
import com.example.board.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지 이동
    @GetMapping("/signupForm")
    public String singup() {
        return "members/signupForm";
    }

    // 회원가입
    @PostMapping("/doSignup")
    public String doSignup(SignupRequest signupRequest) {
        memberService.signup(signupRequest, null);
        return "redirect:/posts";
    }
}
