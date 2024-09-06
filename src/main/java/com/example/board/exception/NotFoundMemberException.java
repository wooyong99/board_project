package com.example.board.exception;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException(String msg) {
        super(msg);
    }
}
