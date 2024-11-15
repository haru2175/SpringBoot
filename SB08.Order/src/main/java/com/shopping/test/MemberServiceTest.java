package com.shopping.test;

import com.shopping.common.GenerateData;
import com.shopping.entity.Member;
import com.shopping.repository.MemberRepository;
import com.shopping.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {
    @Autowired // 오토 와이어링을 이용하여 의미있는 객체를 만들어줍니다.
    MemberService ms ;

    @Test
    @DisplayName("회원 가입 테스트")
    public void saveMember(){
        // member 객체는 실제 html 화면에서 입력한 데이터 정보입니다.
        Member member = GenerateData.createMember();

        // savedMember 객체는 database에 insert된 데이터 정보입니다.
        Member savedMember = ms.saveMember(member) ;

        // 콘솔 창에 출력된 이메일 주소를 다음 테스트에서 사용합니다.
        System.out.println("폼 화면에서 기입한 데이터 정보");
        System.out.println(member);
        System.out.println("\n데이터 베이스에 저장된 정보");
        System.out.println(savedMember);
    }

    @Autowired
    MemberRepository mr ;

    @Test // 이 테스트는 오류가 발생해야 합니다.
    @DisplayName("이메일 중복 가입 테스트")
    public void saveDuplicateMemberTest(){
        // 이전 예시에서 사용한 이메일 주소를 여기에 입력해 주세요.
        String email = "bluesky1947@example.com";

        Member mem01 = mr.findByEmail(email) ;
        Member mem02 = mem01 ; // 참조 복사

        ms.saveMember(mem01) ;

        // 이전에 가입한 다른 회원의 이메일로 저장 시도
        ms.saveMember(mem02) ;
    }
}
