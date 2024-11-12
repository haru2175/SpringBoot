package com.shopping.test;

import com.shopping.common.GenerateData;
import com.shopping.entity.Member;
import com.shopping.repository.MemberRepository;
import com.shopping.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    MemberService ms;

    @Test
    @DisplayName("회원가입 실행 로직 수행")
    public void saveMember() {
        //html 화면에서 입력한 데이터 정보
        Member member = GenerateData.createMember();

        //database에 insert된 정보
        Member savedMember = ms.saveMember(member);

        System.out.println("폼 화면에서 기입한 데이터 정보:" + member);
        System.out.println("데이터베이스에 저장된 정보" + savedMember);

        assertThat(savedMember.getPassword()).isEqualTo(member.getPassword());
    }

    @Autowired
    MemberRepository mr;

    @Test
    @DisplayName("이메일 중복 검사")
    public void saveDuplicateMemberTest() {
        //이전 예시에서 사용한 이메일 주소 입력하면 중복검사 가능
        String email = "world2932@example.com";
        Member mem01 = mr.findByEmail(email);

        Member mem02 = mem01;

        ms.saveMember(mem01);
        //이전에 가입한 다른 회원의 이메일로 저장 시도-> 예외바발생!!
        ms.saveMember(mem02);

    }
}
