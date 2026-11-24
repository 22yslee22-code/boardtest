package com.board2.boardtest.service;

import com.board2.boardtest.dto.BoardDTO;
import com.board2.boardtest.entity.Board;
import com.board2.boardtest.repository.BoardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private static final int PAGE_SIZE = 15;

    // 기존 화면에서 사용하던 날짜 표시 형식을 유지한다.
    private static final DateTimeFormatter LIST_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter DETAIL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final BoardJpaRepository boardRepository;

    // 화면에서 받은 DTO를 Entity로 만들어 저장한다.
    @Transactional
    public void save(BoardDTO boardDTO) {
        Board board = new Board(
                boardDTO.getBoardTitle(),
                boardDTO.getBoardWriter(),
                boardDTO.getBoardContents()
        );

        boardRepository.save(board);
    }

    // 건수와 목록 조회가 같은 시점의 데이터를 사용하도록 처리한다.
    @Transactional(
            readOnly = true,
            isolation = Isolation.REPEATABLE_READ
    )
    public BoardPage findPage(String keyword, long requestedPage) {
        String searchKeyword = keyword.strip();

        long totalCount;
        if (searchKeyword.isEmpty()) {
            totalCount = boardRepository.count();
        } else {
            totalCount = boardRepository
                    .countByBoardTitleContainingIgnoreCase(searchKeyword);
        }

        // 결과가 없어도 화면에서는 1페이지로 처리한다.
        long totalPages = totalCount == 0
                ? 1
                : (totalCount - 1) / PAGE_SIZE + 1;

        // 요청한 페이지를 실제로 존재하는 범위에 맞춘다.
        long page = Math.min(Math.max(requestedPage, 1), totalPages);

        // 화면의 1페이지는 JPA의 0페이지에 해당한다.
        PageRequest pageRequest = PageRequest.of(
                Math.toIntExact(page - 1),
                PAGE_SIZE,
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<Board> result;
        if (searchKeyword.isEmpty()) {
            result = boardRepository.findAll(pageRequest);
        } else {
            result = boardRepository
                    .findByBoardTitleContainingIgnoreCase(
                            searchKeyword,
                            pageRequest
                    );
        }

        // 화면에 필요한 DTO로 변환하면서 목록용 날짜 형식을 적용한다.
        List<BoardDTO> posts = result.getContent()
                .stream()
                .map(board -> toDTO(board, LIST_DATE_FORMAT))
                .toList();

        return new BoardPage(posts, page, totalPages, totalCount);
    }

    // Controller에서 사용하는 기존 결과 형태를 유지한다.
    public record BoardPage(
            List<BoardDTO> posts,
            long page,
            long totalPages,
            long totalCount
    ) {
    }

    // 조회수 증가 쿼리는 쓰기 트랜잭션 안에서 실행한다.
    @Transactional
    public void updateHits(Long id) {
        boardRepository.increaseHits(id);
    }

    // 상세 화면과 수정 화면에 사용할 게시글을 조회한다.
    @Transactional(readOnly = true)
    public BoardDTO findById(Long id) {
        return boardRepository.findById(id)
                .map(board -> toDTO(board, DETAIL_DATE_FORMAT))
                .orElse(null);
    }

    // 조회한 Entity의 제목과 내용을 변경한다.
    @Transactional
    public void update(BoardDTO boardDTO) {
        boardRepository.findById(boardDTO.getId())
                .ifPresent(board -> board.update(
                        boardDTO.getBoardTitle(),
                        boardDTO.getBoardContents()
                ));

        // JPA가 변경 내용을 감지해 트랜잭션 완료 시 UPDATE를 실행한다.
    }

    // 글 번호로 게시글을 삭제한다.
    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    // DB에서 읽은 Entity를 기존 화면에서 사용하는 DTO로 변환한다.
    private BoardDTO toDTO(
            Board board,
            DateTimeFormatter dateFormat
    ) {
        BoardDTO dto = new BoardDTO();
        dto.setId(board.getId());
        dto.setBoardTitle(board.getBoardTitle());
        dto.setBoardWriter(board.getBoardWriter());
        dto.setBoardPass(board.getBoardPass());
        dto.setBoardContents(board.getBoardContents());

        // 기존 DB에 NULL 조회수가 있으면 화면에는 0으로 표시한다.
        dto.setBoardHits(
                board.getBoardHits() == null ? 0 : board.getBoardHits()
        );

        // 기존 DB에서 작성일을 NULL로 허용하므로 확인 후 변환한다.
        if (board.getCreatedAt() != null) {
            dto.setCreatedAt(board.getCreatedAt().format(dateFormat));
        }

        return dto;
    }
}