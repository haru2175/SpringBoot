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
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    // http://localhost:8989/member/insert
    @GetMapping("/insert")
    public String doGetInsert(Model model){
        // html 문서에 참조할 command 객체 이름 `modelView`를 Binding 시킵니다.
        model.addAttribute("memberView", new MemberView());
        return "member/meInsertForm" ;
    }

    // 다음 변수들은 회원 가입시 Post 방식에서 사용됩니다.
    private final PasswordEncoder pe ;
    private final MemberService ms ;

    @PostMapping("/insert")
    public String doPostInsert(@Valid MemberView mv, BindingResult br, Model model){
        String errorMessage = "" ;

        if(br.hasErrors()){
            errorMessage = "회원 가입시 유효성 검사에 문제가 있습니다." ;
            model.addAttribute("errorMessage", errorMessage) ;
            return "member/meInsertForm" ;
        }

        try{ // 회원 가입 성공
            Member member = Member.createMember(mv, pe);
            ms.saveMember(member);
            return "redirect:/" ; // 메인 페이지로 이동해주세요.

        }catch (Exception err){ // 오류 발생
            errorMessage = err.getMessage() ;
            model.addAttribute("errorMessage", errorMessage) ;
            return "member/meInsertForm" ;
        }
    }

    // SecurityConfig.java 파일 내의 securityFilterChain 메소드 참고 요망
    @GetMapping("/login")
    public String doGetLogin(){
        return "member/meLoginForm" ;
    }

    @GetMapping("/login/error")
    public String logonError(Model model){
        System.out.println("로그인에 문제가 발생하였습니다.");
        model.addAttribute("loginErrorMsg", "이메일 주소 또는 비번이 잘못되었습니다.");
        return "member/meLoginForm" ;
    }

}
