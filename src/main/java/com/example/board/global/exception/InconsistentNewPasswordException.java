package com.example.board.global.exception;

public class InconsistentNewPasswordException extends RuntimeException {

    public InconsistentNewPasswordException(String msg) {
        super(msg);
    }
}
