package com.example.board.infrastructure.aop;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.board.domain.entity.RoleEnum;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.http.HttpStatus;

@Retention(RUNTIME)
@Target(METHOD)
public @interface AuthorizationRequired {

    RoleEnum[] value();

    String failureMessage() default "";

    HttpStatus status() default HttpStatus.UNAUTHORIZED;
}
