package com.board2.boardtest.controller;


import com.board2.boardtest.dto.UserDTO;
import com.board2.boardtest.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*회원가입*/
    @GetMapping("/signup")
    public String signup() {
        System.out.println("회원가입");
        return "forward:/WEB-INF/views/signup.jsp";
    }

    /*회원가입 처리*/
    @PostMapping("/signup")
    public String signupSave(@ModelAttribute UserDTO userDTO, Model model) {

        try {
            userService.signup(userDTO);
            return "redirect:/";

        } catch (IllegalArgumentException e) {
            // 중복시 에러메세지
            model.addAttribute("errorMessage", e.getMessage());

            return "forward:/WEB-INF/views/signup.jsp";
        }
    }

    /*로그인 화면*/
    @GetMapping("/login")
    public String loginForm(@RequestParam(defaultValue = "/") String returnUrl, Model model) {

        model.addAttribute("returnUrl", returnUrl);

        return "forward:/WEB-INF/views/login.jsp";
    }
/*    @GetMapping("/login")
    public String loginForm() {
        return "forward:/WEB-INF/views/login.jsp";
    }*/

    /*로그인 처리*/

    /* 로그인 처리 */
    @PostMapping("/login")
    public String login(@ModelAttribute UserDTO userDTO,
                        @RequestParam(defaultValue = "/") String returnUrl,
                        HttpSession session) {

        boolean result = userService.login(userDTO);

        if (result) {
            // 로그인 성공 시 아이디를 세션에 저장
            session.setAttribute("loginId", userDTO.getUserId());

            // 허용된 페이지로만 이동
            if (returnUrl.equals("/list")) {
                return "redirect:/list";
            }

            return "redirect:/";
        }

        // 로그인 실패 시 다시 로그인 화면
        if (returnUrl.equals("/list")) {
            return "redirect:/login?error=true&returnUrl=/list";
        }

        return "redirect:/login?error=true";
    }

/*
    @PostMapping("/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session) {

        boolean result = userService.login(userDTO);

        if (result) {
            session.setAttribute("loginId", userDTO.getUserId()); //로그인에 성공한 아이디를 세션에 저장
            return "redirect:/";
        }

        return "redirect:/login?error=true";
    }
*/

    /*로그아웃 처리*/
    @PostMapping("/logout")
    public String logout(
            @RequestParam(defaultValue = "/") String returnUrl,
            HttpSession session) {

        session.invalidate();

        if (returnUrl.equals("/list")) {
            return "redirect:/list";
        }

        return "redirect:/";
    }
/*    @PostMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }*/


}
