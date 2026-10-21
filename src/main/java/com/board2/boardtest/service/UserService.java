package com.board2.boardtest.service;

import com.board2.boardtest.dto.UserDTO;
import com.board2.boardtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /*회원가입*/
    public void signup(UserDTO userDTO) {
        // 1. 아이디 중복 확인
        UserDTO userID = userRepository.findById(userDTO.getUserId());

        if (userID != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 2. 회원 정보 저장
        userRepository.save(userDTO);
    }

    /* 로그인*/
    public boolean login(UserDTO userDTO) {

        // DB에서 입력한 아이디 조회
        UserDTO user = userRepository.findById(userDTO.getUserId());

        // 아이디가 없으면 실패
        if (user == null) {
            return false;
        }

        // 비밀번호 일치 확인
        return userDTO.getUserPass().equals(user.getUserPass());
    }


}
