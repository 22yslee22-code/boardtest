package com.board2.boardtest.service;


import com.board2.boardtest.dto.BoardDTO;
import com.board2.boardtest.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        boardRepository.save(boardDTO);
    }

    // 레파지토리의 finaAll을 호출한결과를 Controller로 호출한다
    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

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