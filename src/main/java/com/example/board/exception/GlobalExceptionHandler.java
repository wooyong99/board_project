package com.example.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 리소스 접근 실패 핸들러
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity handleAuthorizationException(AuthorizationException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(exception.getMessage());
    }

    // 비밀번호 변경 실패 핸들러
    @ExceptionHandler({InconsistentOriginPasswordException.class,
        InconsistentNewPasswordException.class})
    public ResponseEntity<String> handleUpdatePasswordException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    // 회원가입 실패 핸들러
    @ExceptionHandler({DuplicateMemberException.class})
    public String handleDuplicateUserException(DuplicateMemberException e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());

        return "members/signupForm";
    }

    // 차단 사용자 로그인 실패 핸들러
    @ExceptionHandler({BlockMemberException.class})
    public ResponseEntity handlerBlockMemberException(BlockMemberException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(exception.getMessage());
    }

    // 로그인 실패 핸들러
    @ExceptionHandler({LoginFailedException.class, InCorrectPasswordException.class,
        IllegalArgumentException.class})
    public ResponseEntity handlerInCorrectPasswordException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(exception.getMessage());
    }

    // 회원 조회 실패 핸들러
    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<String> handlerNotFoundBoardException(NotFoundMemberException e) {
        log.error(e.getMessage());
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(e.getMessage());
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
