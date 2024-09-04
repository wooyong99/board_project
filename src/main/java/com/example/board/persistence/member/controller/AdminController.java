package com.example.board.persistence.member.controller;

import com.example.board.persistence.member.dto.MemberInfoResponse;
import com.example.board.persistence.member.dto.SignupRequest;
import com.example.board.persistence.member.dto.SignupServiceDto;
import com.example.board.persistence.member.entity.MemberRoleEnum;
import com.example.board.persistence.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admins")
public class AdminController {

    private final MemberService memberService;

    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입 페이지 이동
    @GetMapping("/signupForm")
    public String singup() {
        return "admins/signupForm";
    }

    // 회원가입 기능
    @PostMapping("/doSignup")
    public String doSignup(@Valid SignupRequest signupRequest) {
        SignupServiceDto serviceDto = new SignupServiceDto(signupRequest);
        memberService.signup(serviceDto, MemberRoleEnum.ADMIN);

        return "redirect:/posts";
    }

    // 회원 검색
    @GetMapping("/search/members")
    public String searchMembers(@RequestParam(required = false, name = "keyword") String keyword,
        @PageableDefault Pageable pageable,
        Model model) {

        Page<MemberInfoResponse> members = memberService.search(keyword, pageable);

        model.addAttribute("members", members);

        return "admins/memberList";
    }

    // 회원 차단
    @PostMapping("/members/{memberId}/block")
    public String blockMember(@PathVariable(name = "memberId") Long memberId) {
        memberService.block(memberId, true);

        return "redirect:/admins/search/members";
    }

    // 회원 차단 해제
    @PostMapping("/members/{memberId}/unblock")
    public String unblockMember(@PathVariable(name = "memberId") Long memberId) {
        memberService.block(memberId, false);

        return "redirect:/admins/search/members";
    }
}
