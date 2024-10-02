package com.example.board.infrastructure.config.security;

import com.example.board.domain.entity.Member;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null
            && SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public static Long getMemberId() {
        Member member = getMember();
        return member == null ? null : member.getId();
    }

    public static Member getMember() {
        return SecurityContextHolder.getContext().getAuthentication() != null ?
            ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal()) :
            null;
    }
}
