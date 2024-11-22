package com.shopping.service;


import com.shopping.entity.Board;
import com.shopping.mapper.BoardMapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // final 이니까
public class BoardService {
    private final BoardMapperInterface bmi ;
    
    // 페이징 처리를 하려면 RowBounds 클래스를 사용하시면 됩니다.

    public List<Board> selectAll(){
        return this.bmi.selectAll() ;

    }
    public int insert(Board board){
        return this.bmi.insert(board);
    }

    public Board selectOne(Integer no){
        return this.bmi.selectOne(no);
    }

    public int update(Board board){
        return this.bmi.update(board);
    }

    public int delete(Integer no){
        return this.bmi.delete(no);
    }
}
