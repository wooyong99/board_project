package com.example.board.exception;

public class InCorrectPasswordException extends RuntimeException {

    public InCorrectPasswordException(String msg) {
        super(msg);
    }
}
