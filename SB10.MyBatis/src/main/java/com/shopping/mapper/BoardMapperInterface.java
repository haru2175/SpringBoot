package com.shopping.mapper;


// mybatis의 이전 버전 이름이 ibatis입니다.

import com.shopping.entity.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

// interface 대신에 xml 문서를 이용하기도 합니다.
@Mapper // 스프링은 이 객체를 자동으로 생성해주고, sql 구문들을 분석하여 처리해 줍니다.
public interface BoardMapperInterface {
    // 게시물 목록 보기
    @Select("select * from boards ")
    List<Board> selectAll();

    // 게시물 등록 하기
    // no 컬럼은 auto increment 옵션에 의하여 자동으로 채워집니다.
    @Insert("insert into boards(writer, subject, description) values(#{board.writer}, #{board.subject}, #{board.description})") // # > ?(JDBC) placeholder
    int insert(@Param("board") final Board board);

    // 게시물 상세 보기
    @Select("select * from boards where no = #{no} ")
    Board selectOne(@Param("no") final Integer no);

    // 게시물 수정 하기
    @Update("update boards set writer=#{board.writer}, subject=#{board.subject}, description=#{board.description} where no=#{board.no} ")
    int update(@Param("board") final Board board);

    // 게시물 삭제 하기
    @Delete("delete from boards where no = #{no} ")
    int delete(@Param("no") final Integer no);
}
