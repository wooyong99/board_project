package com.example.board.infrastructure.exception;

public class InconsistentOriginPasswordException extends RuntimeException {

    public InconsistentOriginPasswordException(String msg) {
        super(msg);
    }
}
