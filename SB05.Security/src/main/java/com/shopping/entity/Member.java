package com.shopping.entity;

import com.shopping.constant.Role;
import com.shopping.view.MemberView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


@Entity
@Table(name = "members")
@Getter @Setter @ToString
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    private String name ;

    @Column(unique = true)
    private String email ;

    private String password ;
    private String address ;

    @Enumerated(EnumType.STRING)
    private Role role;


    // 기입한 회원 정보를 이용하여9 Entity 형태로 반환해 줍니다.
    public static Member createMember(MemberView mv, PasswordEncoder pe){
        // mv : 사용자가 Form에서 입력한 값 정보를 저장하고 있는 command 객체입니다.
        // pe : 입력한 비밀 번호를 암호화 변환해주는 객체입니다.
        Member member = new Member();

        //  member.setId(OL); // 아이디는 자동으로 생성됨
        member.setAddress(mv.getAddress());
        member.setEmail(mv.getEmail());
        member.setRole(Role.USER);
        member.setName(mv.getName());

        String password = pe.encode(mv.getPassword());
        member.setPassword(password); // 암호화된 비밀 번호

        return member;
    }
}
