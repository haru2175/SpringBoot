package com.shopping.entity;

import com.shopping.constant.Role;
import com.shopping.view.MemberView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


@Entity  //Entity 클래스
@Table(name="members")  //Entity 클래스의 필드를 DB table members 와 매핑
@Getter
@Setter
@ToString
public class Member {
    @Id //기본 키 지정
    @Column(name = "member_id")  //컬럼의 이름을 지정
    @GeneratedValue(strategy = GenerationType.AUTO)  //기본 키 생성 방식 설정
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    //기입한 회원 정보를 이용하여 Entity 형태로 변환
    public static Member createMember(MemberView mv, PasswordEncoder pe) {
        //mv: 사용자가 Form에서 입력 값 정보를 저장하고 있는 command 객체
        //pe: 입력 비밀번호 암호화 변환 객체

        Member member = new Member();

        member.setId(0L);
        member.setName(mv.getName());
        member.setEmail(mv.getEmail());

        String password = pe.encode(mv.getPassword());
        member.setPassword(password);  //암호화 된 비밀번호
        
        member.setAddress(mv.getAddress());
        member.setRole(Role.USER);

        return member;

    }
}
