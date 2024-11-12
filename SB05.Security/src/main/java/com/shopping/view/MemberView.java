package com.shopping.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

// command 객체 : HTML 화면에 기입한 내용을 저장하는 자바 클래스입니다.
// 컨트롤러 메소드의 매개 변수로 입력이 가능합니다.
@Getter @Setter @ToString
public class MemberView {
    // @NotBlank는 문자열에만 사용됩니다.
    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String name;

    // @NotEmpty 문자열, 컬렉션에 사용됩니다.
    @NotEmpty(message = "이메일은 필수 입력 사항입니다.")
    @Email(message = "올바른 이메일은 형식으로 입력해 주세요.")
    private String email;

    @NotEmpty(message = "비밀번호은 필수 입력 사항입니다.")
    @Length(min = 8, max = 16 , message = "비밀 번호는 8자리 이상, 16자리 이하로 입력해 주세요.")
    private String password;

    @NotEmpty(message = "주소는 필수 입력 사항입니다.")
    private String address ;
}