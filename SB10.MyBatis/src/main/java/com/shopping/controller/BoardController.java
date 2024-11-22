package com.shopping.controller;


import com.shopping.entity.Board;
import com.shopping.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService bs ;

    // 상단 메뉴에서 `게시물 보기` 링크를 클릭하셨습니다.
    @GetMapping(value = "/list")
    public String selectAll(Model model){
        model.addAttribute("list", bs.selectAll());
        return "/board/boardList";
    }

    // 게시물 `등록하기` 버튼을 누르셨습니다.
    @GetMapping(value = "/insert")
    public String doGetInsert(Model model){
        model.addAttribute("board",new Board());
        return "/board/boardInsert";
    }

    // 게시물 등록 페이지에서 `게시물 등록하기` 버튼을 누르셨습니다.
    // board 객체는 Form 화면에서 넘어온 command 객체입니다.
    @PostMapping(value = "/insert")
    public String doPostInsert(Board board){
        System.out.println(this.getClass() + " board : " + board);

        int cnt = -1;
        cnt = bs.insert(board);

        if(cnt == 1){ // 인서트 성공시 목록 보기 페이지로 이동
            return "redirect:/board/list";
        }else {
            return "/board/boardInsert";
        }
    }

    // 특정 게시물에 대한 상세 보기를 진행합니다.
    @GetMapping(value = "/detail/{no}")
    public String selectOne(@PathVariable("no") Integer no, Model model){
        Board board = this.bs.selectOne(no);
        model.addAttribute("board",board);
        return "/board/boardDetail";
    }

    // 게시물 목록에서 `수정` 링크를 클릭하셨습니다.
    @GetMapping(value = "/update/{no}")
    public String doGetUpdate(@PathVariable("no") Integer no, Model model){
        Board board = this.bs.selectOne(no);
        model.addAttribute("board",board);
        return "/board/boardUpdate";
    }

    // 게시물 수정 페이지에서 `게시물 수정` 버튼을 누르셨습니다.
    // board 객체는 Form 화면에서 넘어온 command 객체입니다.
    @PostMapping(value = "/update")
    public String doPostUpdate(Board board){
        System.out.println(this.getClass() + " board : " + board);

        int cnt = -1;
        cnt = bs.update(board);

        if(cnt == 1){ // 업데이트 성공시 목록 보기 페이지로 이동
            return "redirect:/board/list";
        }else {
            return "/board/boardUpdate";
        }
    }

    // 특정 게시물에 대하여 삭제 기능을 구현합니다.
    @GetMapping(value = "/delete/{no}")
    public String delete(@PathVariable("no") Integer no){
        int cnt = -1;
        cnt = this.bs.delete(no);
        return "redirect:/board/list";
    }
}
