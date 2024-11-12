package com.shopping.service;

import com.shopping.entity.Member;
import com.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service  //로직(Controller)
@RequiredArgsConstructor  //Lombok, final or @NotNull 변수에 자동 '생성자 주입(DI)'
public class MemberService implements UserDetailsService {
    private final MemberRepository mr;

    @Override //사용자의 이름/이메일/주소 등 입력 후, 사용자 권한 정보 등을 반환
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Member 클래스의 email 객체(칼럼) 와 매개변수 String email 연관
        Member member = mr.findByEmail(email);
        if (member == null) {  //이메일이 데이터베이스에 없으면 존재하지 않는 회원
            System.out.println("존재하지 않는 회원");
            throw new UsernameNotFoundException(email);
        }

        //존재하는 회원일 시 해당 로직 수행
        //import org.springframework.security.core.userdetails.User;
        //스프링 프레임워크에서 제공하는 User 클래스를 이용하여 매개변수로 입력받은 e-mail 대한 정보를 반환
        //여기서는 데이터베이스에서 찾은 유저의 이메일/비밀번호/권한 반환
        return User.builder()
                .username(email)  
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    //이메일 사용, 회원 중복 체크 성공하면 Member 엔터티 Database 저장
    public Member saveMember(Member member) {
        this.validateDuplicateMember(member);
        return mr.save(member);
    }

    //Database 내 동일한 email 존재 여부 검토, 존재하면 중복 예외 발생
    private void validateDuplicateMember(Member member) {
        Member findMember = mr.findByEmail(member.getEmail());
        if (findMember != null) {  //이메일이 이미 데이터베이스에 있다면,
            String message = "이미 사용 중인 이메일입니다.";
            throw new IllegalStateException(message);
        }
    }
}
