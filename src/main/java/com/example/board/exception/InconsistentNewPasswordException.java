package com.example.board.exception;

public class InconsistentNewPasswordException extends RuntimeException {

    public InconsistentNewPasswordException(String msg) {
        super(msg);
    }
}
