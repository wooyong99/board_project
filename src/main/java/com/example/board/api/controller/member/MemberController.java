package com.example.board.api.controller.member;

import com.example.board.api.controller.member.request.NicknameUpdateRequest;
import com.example.board.api.controller.member.request.PasswordUpdateRequest;
import com.example.board.api.controller.member.request.SignupRequest;
import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.application.usecase.member.DeleteMemberUseCase;
import com.example.board.application.usecase.member.GetMemberInfoUseCase;
import com.example.board.application.usecase.member.SignupMemberUseCase;
import com.example.board.application.usecase.member.UpdateNicknameUseCase;
import com.example.board.application.usecase.member.UpdatePasswordUseCase;
import com.example.board.application.usecase.member.dto.DeleteMemberServiceRequest;
import com.example.board.application.usecase.member.dto.GetMemberInfoServiceRequest;
import com.example.board.application.usecase.member.dto.SignupMemberServiceRequest;
import com.example.board.application.usecase.member.dto.UpdateNicknameServiceRequest;
import com.example.board.application.usecase.member.dto.UpdatePasswordServiceRequest;
import com.example.board.domain.entity.RoleEnum;
import com.example.board.infrastructure.aop.AuthorizationRequired;
import com.example.board.infrastructure.config.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final SignupMemberUseCase signupMemberUseCase;
    private final GetMemberInfoUseCase getMemberInfoUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final UpdateNicknameUseCase updateNicknameUseCase;
    private final DeleteMemberUseCase deleteMemberUseCase;


    @Autowired
    public MemberController(
        SignupMemberUseCase signupMemberUseCase,
        GetMemberInfoUseCase getMemberInfoUseCase, UpdateNicknameUseCase updateNicknameUseCase,
        UpdatePasswordUseCase updatePasswordUseCase, DeleteMemberUseCase deleteMemberUseCase) {
        this.signupMemberUseCase = signupMemberUseCase;
        this.getMemberInfoUseCase = getMemberInfoUseCase;
        this.updateNicknameUseCase = updateNicknameUseCase;
        this.updatePasswordUseCase = updatePasswordUseCase;
        this.deleteMemberUseCase = deleteMemberUseCase;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupRequest signupRequest) {
        SignupMemberServiceRequest serviceDto = new SignupMemberServiceRequest(
            signupRequest.getNickname(),
            signupRequest.getEmail(), signupRequest.getPassword(), List.of(RoleEnum.USER));
        signupMemberUseCase.signup(serviceDto);

        return ResponseEntity.created(URI.create("/")).build();
    }

    // 자신의 정보 조회
    @GetMapping("/profile")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity memberInfo() {
        Long memberId = SecurityUtils.getMemberId();
        GetMemberInfoServiceRequest serviceDto = new GetMemberInfoServiceRequest(memberId);
        MemberInfoResponse memberInfo = getMemberInfoUseCase.getMemberInfo(serviceDto);

        return ResponseEntity.ok(memberInfo);
    }

    // 닉네임 변경
    @PutMapping("/nickname")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity updateNickname(@RequestBody NicknameUpdateRequest nicknameUpdateRequest) {
        Long memberId = SecurityUtils.getMemberId();
        UpdateNicknameServiceRequest serviceDto = new UpdateNicknameServiceRequest(
            nicknameUpdateRequest.getNewNickname(), memberId);
        updateNicknameUseCase.updateNickname(serviceDto);

        return ResponseEntity.noContent().build();
    }

    // 비밀번호 변경
    @PutMapping("/password")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        Long memberId = SecurityUtils.getMemberId();
        UpdatePasswordServiceRequest serviceDto = new UpdatePasswordServiceRequest(
            passwordUpdateRequest.getOriginPassword(), passwordUpdateRequest.getNewPassword(),
            passwordUpdateRequest.getNewPasswordConfirm(), memberId);
        updatePasswordUseCase.updatePassword(serviceDto);

        return ResponseEntity.noContent().build();
    }

    // 회원 탈퇴
    @DeleteMapping
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity deleteMember(HttpServletRequest request, HttpServletResponse response) {
        Long memberId = SecurityUtils.getMemberId();
        DeleteMemberServiceRequest serviceDto = new DeleteMemberServiceRequest(memberId, request,
            response);
        deleteMemberUseCase.delete(serviceDto);
        return ResponseEntity.noContent().build();  // 로그아웃 url로 이동
    }
}
