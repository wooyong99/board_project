package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.SignupRequest;
import com.example.board.domain.member.entity.MemberRoleEnum;
import com.example.board.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/signupForm")
    public String singup() {
        return "admins/signupForm";
    }

    @PostMapping("/doSignup")
    public String doSignup(@Valid SignupRequest signupRequest) {
        memberService.signup(signupRequest, MemberRoleEnum.ADMIN);
        
        return "redirect:/posts";
    }
}
