package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.MemberRoleEnum;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoResponse {

    private Long memberId;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
    private MemberRoleEnum role;

}
