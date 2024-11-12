package com.shopping.controller;

import com.shopping.entity.Member;
import com.shopping.service.MemberService;
import com.shopping.view.MemberView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor  //final 변수 DI
public class MemberController {
    @GetMapping("/insert")
    public String doGetInsert(Model model) {
        //html 문서 참조 command 객체 이름 'modelView' 를 바인딩
        model.addAttribute("memberView", new MemberView());
        return "member/memberInsertForm";
    }

    //회원가입 Post 방식에 사용되는 DI 객체들
    private final PasswordEncoder pe;
    private final MemberService ms;

    @PostMapping("/insert")
    public String doPostInsert(@Valid MemberView mv, BindingResult br, Model model) {
        //@Valid 는 MemberView 의 애너테이션과 유효성 검사를 불러와 오류를 검증함

        String errorMessage = "";
        if (br.hasErrors()) {
            errorMessage = "회원 가입 시 유효성 검사 오류";
            model.addAttribute("errorMessage: ", errorMessage);
            return "member/memberInsertForm";
        }

        try {  //회원 가입 성공
            Member member = Member.createMember(mv, pe);
            ms.saveMember(member);
            return "redirect:/"; //메인 페이지로 이동

        } catch (Exception err) {  //오류 발생
            errorMessage = err.getMessage();
            model.addAttribute("errorMessage: ", errorMessage);
            return "member/memberInsertForm";
        }
    }
}
