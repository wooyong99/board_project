package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.SignupMemberServiceRequest;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.MemberRole;
import com.example.board.domain.entity.Role;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.domain.repository.RoleRepository;
import com.example.board.exception.DuplicateMemberException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupMemberUseCaseImpl implements SignupMemberUseCase {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    public SignupMemberUseCaseImpl(PasswordEncoder encoder, MemberRepository memberRepository,
        RoleRepository roleRepository) {
        this.encoder = encoder;
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void signup(SignupMemberServiceRequest dto) {
        if (memberRepository.existsByEmailAndIsDeletedFalse(dto.getEmail())) {
            throw new DuplicateMemberException("\"이미 존재하는 회원입니다.\"");
        }
        Member member = Member.create(dto.getNickname(), dto.getEmail(),
            encoder.encode(dto.getPassword()), null
        );

        List<Role> roles = dto.getRoles().stream()
            .map(role -> roleRepository.findByName(role)
                .orElseThrow(() -> new IllegalArgumentException("Role이 존재하지 않습니다")))
            .collect(Collectors.toList());

        List<MemberRole> memberRoles = roles.stream()
            .map(role -> new MemberRole(member, role))
            .collect(
                Collectors.toList());

        memberRepository.save(member);
    }
}
