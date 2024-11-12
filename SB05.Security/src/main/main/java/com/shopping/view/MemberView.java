package com.shopping.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

//command 객체: 클라이언트 측에서 HTML 화면에 기입한 내용을 저장
//컨트롤러 메서드의 매개 변수 입력 가능
//서블릿의 request.getParameter()
@Getter
@Setter
@ToString
public class MemberView {
    //NotBlank: String Type Only
    //null, 공백문자(스페이스, 탭) 등 공백 문자열을 허용하지 않아야 할 때 사용
    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String name;

    //NotEmpty: String Type, Collection Type
    //null, 빈 문자열("") 또는 빈 컬렉션 등 단순히 값이 비어 있지 않으면 되는 경우 사용
    @NotEmpty(message = "이메일은 필수 입력 사항입니다.")
    @Email(message = "올바른 이메일 형식을 사용해주세요.")
    private String email;

    // `비밀 번호`에 대하여 대문자가 1개 이상이어야 하고, 특수 문자 !@#$% 중에서 반드시 1개가 입력이 되어야
    @NotEmpty(message = "비밀 번호는 필수 입력 사항입니다.")
    @Length(min = 8, max = 16, message = "비밀 번호는 8자리 이상, 16자리 이하로 입력해 주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%])[A-Za-z\\d!@#$%]{8,16}$",
            message = "비밀 번호는 대문자 1개 이상과 특수 문자 '!@#$%' 중 하나 이상을 포함해야 합니다."
    )
    private String password;






    @NotEmpty(message = "주소는 필수 입력 사항입니다.")
    private String address;
}
