package com.example.board.api.controller.member;

import com.example.board.api.controller.member.request.SignupRequest;
import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.application.usecase.member.SearchMemberUseCase;
import com.example.board.application.usecase.member.SignupMemberUseCase;
import com.example.board.application.usecase.member.UpdateBlockStatusUseCase;
import com.example.board.application.usecase.member.dto.SearchMemberServiceDto;
import com.example.board.application.usecase.member.dto.SignupMemberServiceDto;
import com.example.board.application.usecase.member.dto.UpdateBlockStatusServiceDto;
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
        SignupMemberServiceDto serviceDto = new SignupMemberServiceDto(signupRequest.getNickname(),
            signupRequest.getEmail(), signupRequest.getPassword(), MemberRoleEnum.ADMIN);
        signupMemberUseCase.signup(serviceDto);

        return "redirect:/posts";
    }

    // 회원 검색
    @GetMapping("/search/members")
    public String searchMembers(@RequestParam(required = false, name = "keyword") String keyword,
        @PageableDefault Pageable pageable,
        Model model) {

        SearchMemberServiceDto serviceDto = new SearchMemberServiceDto(keyword, pageable);
        Page<MemberInfoResponse> members = searchMemberUseCase.search(serviceDto);

        model.addAttribute("members", members);

        return "admins/memberList";
    }

    // 회원 차단
    @PostMapping("/members/{memberId}/block")
    public String blockMember(@PathVariable(name = "memberId") Long memberId) {
        UpdateBlockStatusServiceDto serviceDto = new UpdateBlockStatusServiceDto(memberId, true);
        updateBlockStatusUseCase.updateBlockStatus(serviceDto);

        return "redirect:/admins/search/members";
    }

    // 회원 차단 해제
    @PostMapping("/members/{memberId}/unblock")
    public String unblockMember(@PathVariable(name = "memberId") Long memberId) {
        UpdateBlockStatusServiceDto serviceDto = new UpdateBlockStatusServiceDto(memberId, false);
        updateBlockStatusUseCase.updateBlockStatus(serviceDto);

        return "redirect:/admins/search/members";
    }
}
