package com.example.board.infrastructure.persistence.inquiry;

import com.example.board.domain.entity.Inquiry;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryJpaRepository extends JpaRepository<Inquiry, Long> {

    boolean existsByMemberIdAndIsDeletedFalse(Long id);

    Optional<Inquiry> findByIdAndIsDeletedFalse(Long inquiryId);
}
