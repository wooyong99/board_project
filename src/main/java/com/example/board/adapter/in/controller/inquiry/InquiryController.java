package com.example.board.adapter.ports.in.controller.inquiry;

import com.example.board.adapter.ports.in.dto.request.inquiry.InquiryCreateRequest;
import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryDetailResponse;
import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryListResponse;
import com.example.board.application.port.in.inquiry.DeleteInquiryUseCase;
import com.example.board.application.port.in.inquiry.GetInquiryDetailUseCase;
import com.example.board.application.port.in.inquiry.GetInquiryListUseCase;
import com.example.board.application.port.in.inquiry.SaveInquiryUseCase;
import com.example.board.application.service.dto.InquiryCreateServiceDto;
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

    private final SaveInquiryUseCase saveInquiryUseCase;
    private final DeleteInquiryUseCase deleteInquiryUseCase;
    private final GetInquiryListUseCase getInquiryListUseCase;
    private final GetInquiryDetailUseCase getInquiryDetailUseCase;


    @Autowired
    public InquiryController(SaveInquiryUseCase saveInquiryUseCase,
        DeleteInquiryUseCase deleteInquiryUseCase, GetInquiryDetailUseCase getInquiryDetailUseCase,
        GetInquiryListUseCase getInquiryListUseCase) {
        this.saveInquiryUseCase = saveInquiryUseCase;
        this.deleteInquiryUseCase = deleteInquiryUseCase;
        this.getInquiryDetailUseCase = getInquiryDetailUseCase;
        this.getInquiryListUseCase = getInquiryListUseCase;
    }

    // 차단 문의 페이지 이동
    @GetMapping("/inquiryForm")
    public String registerInquiryForm() {
        return "inquiries/inquiryForm";
    }

    // 차단 문의
    @PostMapping("/inquiry")
    public String saveInquiry(InquiryCreateRequest inquiryCreateRequest) {
        InquiryCreateServiceDto serviceDto = new InquiryCreateServiceDto(inquiryCreateRequest);
        saveInquiryUseCase.save(serviceDto);
        return "members/loginForm";
    }

    // 문의 게시글 리스트 조회
    @GetMapping("/inquiry")
    public String getInquiry(@PageableDefault Pageable pageable, Model model) {
        Page<InquiryListResponse> inquiries = getInquiryListUseCase.findList(pageable);

        model.addAttribute("inquiries", inquiries);

        return "inquiries/list";
    }

    // 문의 게시글 단건 조회
    @GetMapping("/inquiry/{inquiryId}")
    public String getInquiry(@PathVariable(name = "inquiryId") Long inquiryId, Model model) {
        InquiryDetailResponse inquiry = getInquiryDetailUseCase.findOne(inquiryId);

        model.addAttribute("inquiry", inquiry);

        return "inquiries/detail";
    }

    // 문의 게시글 삭제
    @PostMapping("/inquiry/{inquiryId}/delete")
    public String deleteInquiry(@PathVariable(name = "inquiryId") Long inquiryId) {
        deleteInquiryUseCase.delete(inquiryId);

        return "redirect:/inquiry";
    }
}
