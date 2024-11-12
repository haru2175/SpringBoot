package com.shopping.service;


import com.shopping.entity.Member;
import com.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // 이 클래스는 로직을 담당하는 서비스 클래스입니다.
@RequiredArgsConstructor // final 이나 @NotNull이 붙어 있는 변수에 자동으로 생성자 주입을 해주는 어노테이션입니다.

public class MemberService {
    private final MemberRepository mr ;


    // 이메일을 사용하여 회원 중복 체크를 성공하면 Member 엔터티를 Database에 저장합니다.
    public Member saveMember(Member member){
        this.validateDuplicateMember(member);
        return mr.save(member);
    }

    // Database에 동일한 email이 존재하는 지 검사하고, 존재하면 예외를 발생 시켜 줍니다.
    private void validateDuplicateMember(Member member) {
        Member findMember = mr.findByEmail(member.getEmail());
        if (findMember != null){
           String message = "이미 가입된 회원입니다." ;
           throw new IllegalStateException(message);
        }
    }
}
