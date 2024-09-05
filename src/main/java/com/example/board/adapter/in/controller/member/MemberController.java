package com.example.board.adapter.ports.in.controller.member;

import com.example.board.adapter.ports.in.dto.request.member.NicknameUpdateRequest;
import com.example.board.adapter.ports.in.dto.request.member.PasswordUpdateRequest;
import com.example.board.adapter.ports.in.dto.request.member.SignupRequest;
import com.example.board.adapter.ports.in.dto.response.member.MemberInfoResponse;
import com.example.board.application.port.in.member.DeleteMemberUseCase;
import com.example.board.application.port.in.member.GetMemberInfoUseCase;
import com.example.board.application.port.in.member.SignupMemberUseCase;
import com.example.board.application.port.in.member.UpdateNicknameUseCase;
import com.example.board.application.port.in.member.UpdatePasswordUseCase;
import com.example.board.application.service.dto.NicknameUpdateServiceDto;
import com.example.board.application.service.dto.PasswordUpdateServiceDto;
import com.example.board.application.service.dto.SignupServiceDto;
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
        SignupServiceDto serviceDto = new SignupServiceDto(signupRequest);
        signupMemberUseCase.signup(serviceDto, null);
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
        MemberInfoResponse memberInfo = getMemberInfoUseCase.getMemberInfo(principal.getName());

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
        NicknameUpdateServiceDto serviceDto = new NicknameUpdateServiceDto(nicknameUpdateRequest);
        updateNicknameUseCase.updateNickname(serviceDto, principal.getName());

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
        PasswordUpdateServiceDto serviceDto = new PasswordUpdateServiceDto(passwordUpdateRequest);
        updatePasswordUseCase.updatePassword(serviceDto, principal.getName());

        return "redirect:/members/info";
    }

    // 회원 탈퇴
    @PostMapping("/delete")
    public String deleteMember(Principal principal) {
        deleteMemberUseCase.delete(principal.getName());

        return "redirect:/logout";  // 로그아웃 url로 이동
    }
}
