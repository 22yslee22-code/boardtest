// MyBatis 학습 기록: 게시글 DB 접근을 BoardJpaRepository로 전환하여 전체 코드를 비활성화한다.
// 기존 SqlSessionTemplate과 XML 매퍼 호출 방식을 비교 학습하기 위해 원문을 보관한다.
// package com.board2.boardtest.repository;
//
//
// import com.board2.boardtest.dto.BoardDTO;
// import lombok.RequiredArgsConstructor;
// import org.mybatis.spring.SqlSessionTemplate;
// import org.springframework.stereotype.Repository;
//
// import java.util.List;
// import java.util.Map;
//
//
// @Repository
// @RequiredArgsConstructor
// public class BoardRepository {
//
//     private final SqlSessionTemplate sql; //맵퍼호출
//
//     public void save(BoardDTO boardDTO) {
//         sql.insert("Board.save", boardDTO);
//     }
//
//     public long countPosts(String keyword) {
//         return sql.selectOne("Board.countPosts", Map.of("keyword", keyword));
//     }
//
//     public List<BoardDTO> findPage(String keyword, int limit, long offset) {
//         return sql.selectList("Board.findPage", Map.of("keyword", keyword, "limit", limit, "offset", offset));
//     }
//
//     public void updateHits(Long id) {
//
//         sql.update("Board.updateHits", id); // 조회수 업데이트 쿼리 수행
//     }
//
//     public BoardDTO findById(Long id) {
//         return sql.selectOne("Board.findById", id); // 조회수 내용 select
//     }
//
//     // 게시글 업데이트
//     public void update(BoardDTO boardDTO) {
//
//         sql.update("Board.update", boardDTO);
//     }
//
//     // 게시글 삭제
//     public void delete(Long id) {
//         sql.delete("Board.delete", id);
//     }
// }
