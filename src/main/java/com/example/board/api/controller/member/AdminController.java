package com.example.board.api.controller.member;

import com.example.board.api.controller.member.request.AdminSignupRequest;
import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.application.usecase.member.SearchMemberUseCase;
import com.example.board.application.usecase.member.SignupMemberUseCase;
import com.example.board.application.usecase.member.UpdateBlockStatusUseCase;
import com.example.board.application.usecase.member.dto.SearchMemberServiceRequest;
import com.example.board.application.usecase.member.dto.SignupMemberServiceRequest;
import com.example.board.application.usecase.member.dto.UpdateBlockStatusServiceRequest;
import com.example.board.domain.entity.RoleEnum;
import com.example.board.infrastructure.aop.AuthorizationRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

    // 관리자 회원가입 기능
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody AdminSignupRequest adminSignupRequest) {
        SignupMemberServiceRequest serviceDto = new SignupMemberServiceRequest(
            adminSignupRequest.getNickname(),
            adminSignupRequest.getEmail(), adminSignupRequest.getPassword(),
            adminSignupRequest.getRoles());
        signupMemberUseCase.signup(serviceDto);

        return ResponseEntity.ok().build();
    }

    // 회원 검색 ( 관리자 역할 유저만 접근 가능 )
    @GetMapping("/search/members")
    @AuthorizationRequired(value = {
        RoleEnum.EXERCISE_ADMIN}, failureMessage = "관리자만 접근 가능한 페이지입니다.")
    public ResponseEntity searchMembers(
        @RequestParam(required = false, name = "keyword") String keyword,
        @PageableDefault Pageable pageable) {
        SearchMemberServiceRequest serviceDto = new SearchMemberServiceRequest(keyword, pageable);
        Page<MemberInfoResponse> members = searchMemberUseCase.search(serviceDto);

        return ResponseEntity.ok(members);
    }

    // 회원 차단 ( 관리자 역할 유저만 접근 가능 )
    @PutMapping("/members/{memberId}/block")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN})
    public ResponseEntity blockMember(@PathVariable(name = "memberId") Long memberId) {
        UpdateBlockStatusServiceRequest serviceDto = new UpdateBlockStatusServiceRequest(memberId,
            true);
        updateBlockStatusUseCase.updateBlockStatus(serviceDto);

        return ResponseEntity.ok().build();
    }

    // 회원 차단 해제 ( 관리자 역할 유저만 접근 가능 )
    @PutMapping("/members/{memberId}/unblock")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN})
    public ResponseEntity unblockMember(@PathVariable(name = "memberId") Long memberId) {
        UpdateBlockStatusServiceRequest serviceDto = new UpdateBlockStatusServiceRequest(memberId,
            false);
        updateBlockStatusUseCase.updateBlockStatus(serviceDto);

        return ResponseEntity.ok().build();
    }
}
