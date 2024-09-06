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
import com.example.board.application.usecase.member.dto.DeleteMemberServiceDto;
import com.example.board.application.usecase.member.dto.GetMemberInfoServiceDto;
import com.example.board.application.usecase.member.dto.SignupMemberServiceDto;
import com.example.board.application.usecase.member.dto.UpdateNicknameServiceDto;
import com.example.board.application.usecase.member.dto.UpdatePasswordServiceDto;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final SignupMemberUseCase signupMemberUseCase;
    private final GetMemberInfoUseCase getMemberInfoUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final UpdateNicknameUseCase updateNicknameUseCase;
    private final DeleteMemberUseCase deleteMemberUseCase;

    @Autowired
    public MemberController(SignupMemberUseCase signupMemberUseCase,
        GetMemberInfoUseCase getMemberInfoUseCase, UpdateNicknameUseCase updateNicknameUseCase,
        UpdatePasswordUseCase updatePasswordUseCase, DeleteMemberUseCase deleteMemberUseCase) {
        this.signupMemberUseCase = signupMemberUseCase;
        this.getMemberInfoUseCase = getMemberInfoUseCase;
        this.updateNicknameUseCase = updateNicknameUseCase;
        this.updatePasswordUseCase = updatePasswordUseCase;
        this.deleteMemberUseCase = deleteMemberUseCase;
    }

    // 회원가입 페이지 이동
    @GetMapping("/signupForm")
    public String singup() {
        return "members/signupForm";
    }

    // 회원가입
    @PostMapping("/doSignup")
    public String doSignup(@Valid SignupRequest signupRequest) {
        SignupMemberServiceDto serviceDto = new SignupMemberServiceDto(signupRequest.getNickname(),
            signupRequest.getEmail(), signupRequest.getPassword(), null);
        signupMemberUseCase.signup(serviceDto);

        return "redirect:/posts";
    }

    // 로그인 페이지 이동
    @GetMapping("/loginForm")
    public String login(@RequestParam(required = false) String errorMessage, Model model) {
        if (errorMessage != null) {
            model.addAttribute("errorMessage", "\"" + errorMessage + "\"");
        }
        return "members/loginForm";
    }

    // 마이페이지
    @GetMapping("/info")
    public String memberInfo(Principal principal, Model model) {
        GetMemberInfoServiceDto serviceDto = new GetMemberInfoServiceDto(principal.getName());
        MemberInfoResponse memberInfo = getMemberInfoUseCase.getMemberInfo(serviceDto);

        model.addAttribute("memberInfo", memberInfo);

        return "members/mypage";
    }

    // 닉네임 변경 페이지 이동
    @GetMapping("/updateNicknameForm")
    public String updateNicknameForm() {
        return "members/updateNicknameForm";
    }

    // 닉네임 변경
    @PostMapping("/updateNickname")
    public String updatePassowrd(NicknameUpdateRequest nicknameUpdateRequest, Principal principal) {
        UpdateNicknameServiceDto serviceDto = new UpdateNicknameServiceDto(
            nicknameUpdateRequest.getNewNickname(), principal.getName());
        updateNicknameUseCase.updateNickname(serviceDto);

        return "redirect:/members/info";
    }

    // 비밀번호 변경 페이지 이동
    @GetMapping("/updatePasswordForm")
    public String updatePasswordForm() {
        return "members/updatePasswordForm";
    }

    // 비밀번호 변경
    @PostMapping("/updatePassword")
    public String updatePassowrd(PasswordUpdateRequest passwordUpdateRequest, Principal principal) {
        UpdatePasswordServiceDto serviceDto = new UpdatePasswordServiceDto(
            passwordUpdateRequest.getOriginPassword(), passwordUpdateRequest.getNewPassword(),
            passwordUpdateRequest.getNewPasswordConfirm(), principal.getName());
        updatePasswordUseCase.updatePassword(serviceDto);

        return "redirect:/members/info";
    }

    // 회원 탈퇴
    @PostMapping("/delete")
    public String deleteMember(Principal principal) {
        DeleteMemberServiceDto serviceDto = new DeleteMemberServiceDto(principal.getName());
        deleteMemberUseCase.delete(serviceDto);

        return "redirect:/logout";  // 로그아웃 url로 이동
    }
}
