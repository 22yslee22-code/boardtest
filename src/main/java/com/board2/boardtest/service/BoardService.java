package com.board2.boardtest.service;


import com.board2.boardtest.dto.BoardDTO;
import com.board2.boardtest.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private static final int PAGE_SIZE = 15;
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        boardRepository.save(boardDTO);
    }

    // 결과 건수와 목록이 같은 시점의 데이터를 사용하도록 조회한다.
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public BoardPage findPage(String keyword, long requestedPage) {
        long totalCount = boardRepository.countPosts(keyword);
        long totalPages = totalCount == 0 ? 1 : (totalCount - 1) / PAGE_SIZE + 1;
        // 범위를 벗어나면 첫 페이지 또는 마지막 페이지를 보여준다.
        long page = Math.min(Math.max(requestedPage, 1), totalPages);
        long offset = (page - 1) * PAGE_SIZE;
        List<BoardDTO> posts = boardRepository.findPage(keyword, PAGE_SIZE, offset);
        return new BoardPage(posts, page, totalPages, totalCount);
    }

    public record BoardPage(List<BoardDTO> posts, long page, long totalPages, long totalCount) {}

    // 조회수
    public void updateHits(Long id) {
        boardRepository.updateHits(id); // id를 같이 넘김
    }

    // 게시물 내용 가져오기
    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    //게시글 삭제
    public void delete(Long id) {
        boardRepository.delete(id);
    }
}
