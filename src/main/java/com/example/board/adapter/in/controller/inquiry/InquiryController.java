package com.example.board.adapter.ports.in.controller.inquiry;

import com.example.board.adapter.ports.in.dto.request.inquiry.InquiryCreateRequest;
import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryDetailResponse;
import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryListResponse;
import com.example.board.application.service.dto.InquiryCreateServiceDto;
import com.example.board.application.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InquiryController {

    private final InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    // 차단 문의 페이지 이동
    @GetMapping("/inquiryForm")
    public String registerInquiryForm() {
        return "inquiries/inquiryForm";
    }

    // 차단 문의
    @PostMapping("/inquiry")
    public String registerInquiry(InquiryCreateRequest inquiryCreateRequest) {
        InquiryCreateServiceDto serviceDto = new InquiryCreateServiceDto(inquiryCreateRequest);
        inquiryService.save(serviceDto);
        return "members/loginForm";
    }

    // 문의 게시글 리스트 조회
    @GetMapping("/inquiry")
    public String getInquiry(@PageableDefault Pageable pageable, Model model) {
        Page<InquiryListResponse> inquiries = inquiryService.findList(pageable);

        model.addAttribute("inquiries", inquiries);

        return "inquiries/list";
    }

    // 문의 게시글 단건 조회
    @GetMapping("/inquiry/{inquiryId}")
    public String getInquiry(@PathVariable(name = "inquiryId") Long inquiryId, Model model) {
        InquiryDetailResponse inquiry = inquiryService.findOne(inquiryId);

        model.addAttribute("inquiry", inquiry);

        return "inquiries/detail";
    }

    // 문의 게시글 삭제
    @PostMapping("/inquiry/{inquiryId}/delete")
    public String deleteInquiry(@PathVariable(name = "inquiryId") Long inquiryId) {
        inquiryService.delete(inquiryId);

        return "redirect:/inquiry";
    }
}
