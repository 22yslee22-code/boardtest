package com.board2.boardtest.service;

import com.board2.boardtest.dto.UserDTO;
import com.board2.boardtest.entity.User;
import com.board2.boardtest.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    // 회원가입 중 수행하는 DB 작업을 하나의 트랜잭션으로 처리한다.
    @Transactional
    public void signup(UserDTO userDTO) {

        // 이미 가입된 아이디인지 확인한다.
        if (userRepository.existsById(userDTO.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 화면에서 받은 DTO를 DB에 저장할 Entity로 변환한다.
        User user = new User(
                userDTO.getUserId(),
                userDTO.getUserPass()
        );

        // 사용자가 직접 정한 아이디로 새 회원을 등록한다.
        // 동일한 기본키가 있으면 DB의 기본키 제약에 따라 저장이 실패한다.
        entityManager.persist(user);
    }

    // 회원 정보를 읽는 작업이므로 읽기 전용 트랜잭션으로 처리한다.
    @Transactional(readOnly = true)
    public boolean login(UserDTO userDTO) {

        // 회원을 찾으면 비밀번호를 비교하고, 회원이 없으면 false를 반환한다.
        return userRepository.findById(userDTO.getUserId())
                .map(user -> user.getUserPass().equals(userDTO.getUserPass()))
                .orElse(false);
    }
}