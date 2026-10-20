package com.board2.boardtest.repository;


import com.board2.boardtest.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final SqlSessionTemplate sql; //맵퍼호출

    public void save(BoardDTO boardDTO) {
        sql.insert("Board.save", boardDTO);
    }

    public List<BoardDTO> findAll() {

        return sql.selectList("Board.findAll");
    }

    public void updateHits(Long id) {

        sql.update("Board.updateHits", id); // 조회수 업데이트 쿼리 수행
    }

    public BoardDTO findById(Long id) {
        return sql.selectOne("Board.findById", id); // 조회수 내용 select
    }

    // 게시글 업데이트
    public void update(BoardDTO boardDTO) {

        sql.update("Board.update", boardDTO);
    }

    // 게시글 삭제
    public void delete(Long id) {
        sql.delete("Board.delete", id);
    }
}
