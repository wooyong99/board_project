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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inquiries")
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

    // 차단 문의
    @PostMapping
    public ResponseEntity saveInquiry(@RequestBody InquiryCreateRequest inquiryCreateRequest) {
        SaveInquiryServiceDto serviceDto = new SaveInquiryServiceDto(
            inquiryCreateRequest.getEmail(), inquiryCreateRequest.getPassword(),
            inquiryCreateRequest.getContent()
        );
        saveInquiryUseCase.save(serviceDto);

        return ResponseEntity.noContent().build();
    }

    // 문의 게시글 리스트 조회
    @GetMapping
    public ResponseEntity getInquiry(@PageableDefault Pageable pageable) {
        GetInquiryListServiceDto serviceDto = new GetInquiryListServiceDto(pageable);
        Page<InquiryListResponse> inquiries = getInquiryListUseCase.findList(serviceDto);

        return ResponseEntity.ok(inquiries);
    }

    // 문의 게시글 단건 조회
    @GetMapping("/{inquiryId}")
    public ResponseEntity getInquiry(@PathVariable(name = "inquiryId") Long inquiryId) {
        GetInquiryDetailServiceDto serviceDto = new GetInquiryDetailServiceDto(inquiryId);
        InquiryDetailResponse inquiry = getInquiryDetailUseCase.findOne(serviceDto);

        return ResponseEntity.ok(inquiry);
    }

    // 문의 게시글 삭제
    @PostMapping("/inquiry/{inquiryId}/delete")
    public ResponseEntity deleteInquiry(@PathVariable(name = "inquiryId") Long inquiryId) {
        DeleteInquiryServiceDto serviceDto = new DeleteInquiryServiceDto(inquiryId);
        deleteInquiryUseCase.delete(serviceDto);

        return ResponseEntity.noContent().build();
    }
}
