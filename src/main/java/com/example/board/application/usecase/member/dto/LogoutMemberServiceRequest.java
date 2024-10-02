package com.example.board.application.usecase.member.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogoutMemberServiceRequest {

    private HttpServletRequest request;

    private HttpServletResponse response;

}
