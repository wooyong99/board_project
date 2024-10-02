package com.example.board.infrastructure.config.security;

import org.springframework.security.core.Authentication;

public interface SecurityService {

    Authentication getAuthentication(String userId);
}
