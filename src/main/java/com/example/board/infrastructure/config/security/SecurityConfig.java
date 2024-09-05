package com.example.board.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Bean
    AuthenticationFailureHandler customAuthFailureHandler() {
        return new CustomAuthFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/members/loginForm", "/members/signupForm").permitAll()
                .requestMatchers("/posts/*register").authenticated()
                .requestMatchers("/posts/*registerForm").authenticated()
                .requestMatchers("/posts/*update").authenticated()
                .requestMatchers("/posts/*updateForm").authenticated()
                .requestMatchers("/posts/*delete").authenticated()
                .requestMatchers(HttpMethod.GET, "/inquiry").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/inquiry/*/delete").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/admins/search/members").hasAuthority("ADMIN")
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/members/loginForm")
                .loginProcessingUrl("/members/doLogin")
                .defaultSuccessUrl("/posts")
                .failureUrl("/members/loginForm")
                .failureHandler(new CustomAuthFailureHandler())
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/posts")
                .invalidateHttpSession(true)
            );
        return http.build();
    }
}
