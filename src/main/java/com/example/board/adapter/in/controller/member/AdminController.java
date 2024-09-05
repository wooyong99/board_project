package com.example.board.adapter.ports.in.controller.member;

import com.example.board.adapter.ports.in.dto.request.member.SignupRequest;
import com.example.board.adapter.ports.in.dto.response.member.MemberInfoResponse;
import com.example.board.application.port.in.member.SearchMemberUseCase;
import com.example.board.application.port.in.member.SignupMemberUseCase;
import com.example.board.application.port.in.member.UpdateBlockStatusUseCase;
import com.example.board.application.service.dto.SignupServiceDto;
import com.example.board.domain.entity.MemberRoleEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final SignupMemberUseCase signupMemberUseCase;
    private final SearchMemberUseCase searchMemberUseCase;
    private final UpdateBlockStatusUseCase updateBlockStatusUseCase;

    @Autowired
    public AdminController(SignupMemberUseCase signupMemberUseCase,
        SearchMemberUseCase searchMemberUseCase,
        UpdateBlockStatusUseCase updateBlockStatusUseCase) {
        this.signupMemberUseCase = signupMemberUseCase;
        this.searchMemberUseCase = searchMemberUseCase;
        this.updateBlockStatusUseCase = updateBlockStatusUseCase;
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
        signupMemberUseCase.signup(serviceDto, MemberRoleEnum.ADMIN);

        return "redirect:/posts";
    }

    // 회원 검색
    @GetMapping("/search/members")
    public String searchMembers(@RequestParam(required = false, name = "keyword") String keyword,
        @PageableDefault Pageable pageable,
        Model model) {

        Page<MemberInfoResponse> members = searchMemberUseCase.search(keyword, pageable);

        model.addAttribute("members", members);

        return "admins/memberList";
    }

    // 회원 차단
    @PostMapping("/members/{memberId}/block")
    public String blockMember(@PathVariable(name = "memberId") Long memberId) {
        updateBlockStatusUseCase.updateBlockStatus(memberId, true);

        return "redirect:/admins/search/members";
    }

    // 회원 차단 해제
    @PostMapping("/members/{memberId}/unblock")
    public String unblockMember(@PathVariable(name = "memberId") Long memberId) {
        updateBlockStatusUseCase.updateBlockStatus(memberId, false);

        return "redirect:/admins/search/members";
    }
}
