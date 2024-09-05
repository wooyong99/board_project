package com.example.board.infrastructure.exception;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException(String msg) {
        super(msg);
    }
}
