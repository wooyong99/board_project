package com.example.board.infrastructure.exception;

public class DuplicateInquiryException extends RuntimeException {

    public DuplicateInquiryException(String msg) {
        super(msg);
    }
}
