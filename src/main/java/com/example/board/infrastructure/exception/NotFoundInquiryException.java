package com.example.board.infrastructure.exception;

public class NotFoundInquiryException extends RuntimeException {

    public NotFoundInquiryException(String msg) {
        super(msg);
    }
}
