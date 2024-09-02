package com.example.board.global.exception;

public class InCorrectPasswordException extends RuntimeException {

    public InCorrectPasswordException(String msg) {
        super(msg);
    }
}
