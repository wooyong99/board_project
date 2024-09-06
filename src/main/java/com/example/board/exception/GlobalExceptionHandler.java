package com.example.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException {

    // 차단 문의 실패 핸들러
    @ExceptionHandler({DuplicateInquiryException.class})
    public String handleInquiryException(Exception e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("errorMessage", "\"" + e.getMessage() + "\"");

        return "inquiries/inquiryForm";
    }

    // 비밀번호 변경 실패 핸들러
    @ExceptionHandler({InconsistentOriginPasswordException.class,
        InconsistentNewPasswordException.class})
    public String handleUpdatePasswordException(Exception e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("errorMessage", "\"" + e.getMessage() + "\"");

        return "members/updatePasswordForm";
    }

    // 회원가입 실패 핸들러
    @ExceptionHandler({DuplicateMemberException.class})
    public String handleDuplicateUserException(DuplicateMemberException e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());

        return "members/signupForm";
    }

    // 로그인 실패 핸들러
    @ExceptionHandler({InCorrectPasswordException.class, IllegalArgumentException.class})
    public String handlerInCorrectPasswordException(InCorrectPasswordException e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("errorMessage", "\"" + e.getMessage() + "\"");

        return "members/loginForm";
    }

    // 회원 조회 실패 핸들러
    @ExceptionHandler(NotFoundMemberException.class)
    public String handlerNotFoundBoardException(NotFoundMemberException e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("errorMessage", "\"" + e.getMessage() + "\"");

        return "members/loginForm";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNoValidException(MethodArgumentNotValidException e,
        Model model) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError.getDefaultMessage();

        log.error(e.getMessage());

        model.addAttribute("errorMessage", "\"" + errorMessage + "\"");

        return "members/signupForm";
    }

}
