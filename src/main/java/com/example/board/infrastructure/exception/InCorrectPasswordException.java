package com.example.board.infrastructure.exception;

public class InCorrectPasswordException extends RuntimeException {

    public InCorrectPasswordException(String msg) {
        super(msg);
    }
}
