package com.example.board.domain.inquiry.controller;

import com.example.board.domain.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    // 차단 문의 페이지 이동
    @GetMapping("/inquiryForm")
    public String registerInquiryForm() {
        return "inquiries/inquiryForm";
    }

}
