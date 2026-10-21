package com.board2.boardtest.controller;

import com.board2.boardtest.dto.BoardDTO;
import com.board2.boardtest.service.BoardService;
import jakarta.servlet.http.HttpSession;
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

/*
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
    }*/

    /* 글쓰기 화면 */
    @GetMapping("/save")
    public String save(HttpSession session) {

        // 로그인 여부 확인
        String loginId = (String) session.getAttribute("loginId");

        if (loginId == null) {
            return "redirect:/login";
        }

        return "save";
    }

    /* 게시글 저장 */
    @PostMapping("/save")
    public String save(BoardDTO boardDTO, HttpSession session) {

        // 로그인 정보 가져오기
        String loginId = (String) session.getAttribute("loginId");

        // 로그인하지 않았다면 글쓰기 불가
        if (loginId == null) {
            return "redirect:/login";
        }
        // 작성자를 로그인 아이디로 설정
        boardDTO.setBoardWriter(loginId);

        // 게시글 저장
        boardService.save(boardDTO);
        return "redirect:/list";
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
    @GetMapping("/{id:\\d+}") // 글번호는 계속 달라지기 때 문에 {}중괄호
    public String findById(@PathVariable("id") Long id, Model model) {
        // 조회수 카운트 처리
        boardService.updateHits(id);

        //게시물 상세내용 가져옴
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "detail";
    }

    // 게시글 수정 요청

    // 게시글 수정 화면
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model, HttpSession session) {

        String loginId = (String) session.getAttribute("loginId");

        // 로그인 안 했다면 로그인 페이지로
        if (loginId == null) {
            return "redirect:/login";
        }

        // DB에서 게시글 조회
        BoardDTO boardDTO = boardService.findById(id);

        if (boardDTO == null) {
            return "redirect:/list";
        }

        // 내가 작성한 게시글이 아니면 수정 불가
        if (!loginId.equals(boardDTO.getBoardWriter())) {
            return "redirect:/list";
        }

        model.addAttribute("board", boardDTO);

        return "update";
    }

/*    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }*/

    // 게시글 수정 요청 받아줌

    // 게시글 수정 처리
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, BoardDTO boardDTO, HttpSession session) {

        String loginId = (String) session.getAttribute("loginId");

        if (loginId == null) {
            return "redirect:/login";
        }

        // 실제 DB에 저장된 게시글 조회
        BoardDTO originalBoard = boardService.findById(id);

        if (originalBoard == null) {
            return "redirect:/list";
        }

        // 작성자가 다르면 수정 거부
        if (!loginId.equals(originalBoard.getBoardWriter())) {
            return "redirect:/list";
        }

        // 주소의 글 번호를 사용
        boardDTO.setId(id);

        // 작성자는 변경되지 않도록 설정
        boardDTO.setBoardWriter(originalBoard.getBoardWriter());

        boardService.update(boardDTO);

        return "redirect:/" + id;
    }

/*    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, Model model) {
        boardService.update(boardDTO); // 업데이트 후
        BoardDTO dto = boardService.findById(boardDTO.getId()); // 다시 상세 가져와서
        model.addAttribute("board", dto); // 그걸 모델에 담고 detail에 가져오기
        return "detail";
    }*/

    // 게시글 삭제

    // 게시글 삭제
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {

        String loginId = (String) session.getAttribute("loginId");

        if (loginId == null) {
            return "redirect:/login";
        }

        // 실제 DB에 저장된 게시글 확인
        BoardDTO boardDTO = boardService.findById(id);

        if (boardDTO == null) {
            return "redirect:/list";
        }

        // 작성자가 아니면 삭제 불가
        if (!loginId.equals(boardDTO.getBoardWriter())) {
            return "redirect:/list";
        }

        boardService.delete(id);

        return "redirect:/list";
    }


/*    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/list"; // 목록 재요청
    }*/


}
