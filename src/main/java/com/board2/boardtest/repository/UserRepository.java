package com.board2.boardtest.repository;

import com.board2.boardtest.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SqlSessionTemplate sql; //맵퍼호출


    // 회원가입
    public void save(UserDTO userDTO) {
        sql.insert("User.save", userDTO);
    }

    // 회원 중복 확인
    public UserDTO findById(String userId) {
        return sql.selectOne("User.findById", userId);
    }



}
