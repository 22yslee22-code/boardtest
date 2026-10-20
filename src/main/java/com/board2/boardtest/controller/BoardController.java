package com.board2.boardtest.controller;

import com.board2.boardtest.dto.BoardDTO;
import com.board2.boardtest.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor //생성자 생성을 위한 어노테이션
public class BoardController {

    private final BoardService boardService;


    // 게시글 저장
    @GetMapping("/save")
    public String save() {
        return "save";
    }

    //BoardDTO.java에서 받아서 옴
    @PostMapping("/save")
    public String save(BoardDTO boardDTO) {
        System.out.println("boardDTO" + boardDTO);
        boardService.save(boardDTO);
        return "redirect:/list"; // 목록 재요청
    }

    // 글 목록 조회
    @GetMapping("/list")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        System.out.println("boardDTOList = " + boardDTOList); // 출력 확인
        return "list";
    }

    // 게시글 조회
    @GetMapping("/{id}") // 글번호는 계속 달라지기 때 문에 {}중괄호
    public String findById(@PathVariable("id") Long id, Model model) {
        // 조회수 카운트 처리
        boardService.updateHits(id);

        //게시물 상세내용 가져옴
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "detail";
    }

    // 게시글 수정 요청
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }

    // 게시글 수정 요청 받아줌
    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, Model model) {
        boardService.update(boardDTO); // 업데이트 후
        BoardDTO dto = boardService.findById(boardDTO.getId()); // 다시 상세 가져와서
        model.addAttribute("board", dto); // 그걸 모델에 담고 detail에 가져오기
        return "detail";
    }

    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/list"; // 목록 재요청
    }


}
