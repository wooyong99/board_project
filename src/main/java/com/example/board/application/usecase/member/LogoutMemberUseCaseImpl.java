package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.LogoutMemberServiceRequest;
import com.example.board.infrastructure.utils.HttpServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutMemberUseCaseImpl implements LogoutMemberUseCase {

    private final HttpServletUtils servletUtils;

    @Autowired
    public LogoutMemberUseCaseImpl(HttpServletUtils servletUtils) {
        this.servletUtils = servletUtils;
    }

    @Override
    public void logout(LogoutMemberServiceRequest dto) {
        servletUtils.removeCookie(dto.getRequest(), dto.getResponse(), "RefreshToken");
    }
}
