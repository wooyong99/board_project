package com.example.board.infrastructure.exception;

public class InconsistentNewPasswordException extends RuntimeException {

    public InconsistentNewPasswordException(String msg) {
        super(msg);
    }
}
