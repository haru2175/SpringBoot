package com.shopping.service;

import com.shopping.entity.Member;
import com.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // 이 클래스는 로직을 담당하는 서비스 클래스입니다.
@RequiredArgsConstructor // final이나 @NotNull이 붙어 있는 변수에 자동으로 생성자 주입을 해주는 어노테이션입니다.
public class MemberService implements UserDetailsService {
    private final MemberRepository mr ;

    @Override // 사용자의 이름 또는 이메일 주소등을 입력 받아서, 해당 사용자에 대한 권한 정보등을 반환 합니다.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = mr.findByEmail(email) ;
        if(member == null){
            System.out.println("존재하지 않는 회원입니다.");
            throw new UsernameNotFoundException(email) ;
        }
        // User 클래스는 UserDetails 인터페이스의 구현체 클래스입니다.
        return User.builder()
                .username(email)
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    // 이메일을 사용하여 회원 중복 체크를 성공하면 Member 엔터티를 Database에 저장합니다.
    public Member saveMember(Member member){
        this.validateDuplicateMember(member);
        return mr.save(member) ;
    }

    // Database에 동일한 email이 존재하는지 검사하고, 존재하면 예외를 발생 시켜 줍니다.
    private void validateDuplicateMember(Member member) {
        Member findMember = mr.findByEmail(member.getEmail()) ;
        if(findMember != null){
            String message = "이미 가입된 회원입니다.";
            throw new IllegalStateException(message) ;
        }
    }

}
