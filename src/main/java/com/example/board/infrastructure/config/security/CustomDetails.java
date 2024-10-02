package com.example.board.infrastructure.config.security;

import com.example.board.domain.entity.Member;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomDetails implements UserDetails {

    private Member member;

    public CustomDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRoles().toString();
            }
        });
        return collect;
    }

    public boolean isAdmin() {
        return getAuthorities().stream()
            .filter(e -> e.getAuthority().equals("ADMIN"))
            .findFirst()
            .isPresent();
    }

    public Long getId() {
        return member.getId();
    }

    public String getNickname() {
        return member.getNickname();
    }

    public boolean isBlocked() {
        return member.isBlock();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
