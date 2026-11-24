package com.board2.boardtest.repository;

import com.board2.boardtest.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {

    // 제목에 검색어가 포함된 게시글을 대소문자 구분 없이 조회한다.
    // Pageable에서 페이지 번호, 페이지 크기, 정렬 조건을 받는다.
    Page<Board> findByBoardTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    // 페이지 범위를 계산할 때 사용할 검색 결과 건수를 조회한다.
    long countByBoardTitleContainingIgnoreCase(String keyword);

    // 여러 사용자가 동시에 조회해도 DB에서 조회수를 직접 증가시킨다.
    // 기존 데이터의 조회수가 NULL이면 0으로 계산한다.
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
            update Board b
            set b.boardHits = coalesce(b.boardHits, 0) + 1
            where b.id = :id
            """)
    int increaseHits(@Param("id") Long id);
}