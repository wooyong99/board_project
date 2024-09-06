package com.example.board.api.controller.inquiry;

import com.example.board.api.controller.inquiry.request.InquiryCreateRequest;
import com.example.board.api.controller.inquiry.response.InquiryDetailResponse;
import com.example.board.api.controller.inquiry.response.InquiryListResponse;
import com.example.board.application.usecase.inquiry.DeleteInquiryUseCase;
import com.example.board.application.usecase.inquiry.GetInquiryDetailUseCase;
import com.example.board.application.usecase.inquiry.GetInquiryListUseCase;
import com.example.board.application.usecase.inquiry.SaveInquiryUseCase;
import com.example.board.application.usecase.inquiry.dto.DeleteInquiryServiceDto;
import com.example.board.application.usecase.inquiry.dto.GetInquiryDetailServiceDto;
import com.example.board.application.usecase.inquiry.dto.GetInquiryListServiceDto;
import com.example.board.application.usecase.inquiry.dto.SaveInquiryServiceDto;
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
        SaveInquiryServiceDto serviceDto = new SaveInquiryServiceDto(
            inquiryCreateRequest.getEmail(), inquiryCreateRequest.getPassword(),
            inquiryCreateRequest.getContent()
        );
        saveInquiryUseCase.save(serviceDto);
        return "members/loginForm";
    }

    // 문의 게시글 리스트 조회
    @GetMapping("/inquiry")
    public String getInquiry(@PageableDefault Pageable pageable, Model model) {
        GetInquiryListServiceDto serviceDto = new GetInquiryListServiceDto(pageable);
        Page<InquiryListResponse> inquiries = getInquiryListUseCase.findList(serviceDto);

        model.addAttribute("inquiries", inquiries);

        return "inquiries/list";
    }

    // 문의 게시글 단건 조회
    @GetMapping("/inquiry/{inquiryId}")
    public String getInquiry(@PathVariable(name = "inquiryId") Long inquiryId, Model model) {
        GetInquiryDetailServiceDto serviceDto = new GetInquiryDetailServiceDto(inquiryId);
        InquiryDetailResponse inquiry = getInquiryDetailUseCase.findOne(serviceDto);

        model.addAttribute("inquiry", inquiry);

        return "inquiries/detail";
    }

    // 문의 게시글 삭제
    @PostMapping("/inquiry/{inquiryId}/delete")
    public String deleteInquiry(@PathVariable(name = "inquiryId") Long inquiryId) {
        DeleteInquiryServiceDto serviceDto = new DeleteInquiryServiceDto(inquiryId);
        deleteInquiryUseCase.delete(serviceDto);

        return "redirect:/inquiry";
    }
}
