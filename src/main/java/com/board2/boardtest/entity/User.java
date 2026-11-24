package com.board2.boardtest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_user", schema = "public")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    // 기존 DB에서 userid가 기본키이며, 회원이 입력한 아이디를 사용한다.
    @Id
    @Column(name = "userid", length = 20, nullable = false)
    private String userId;

    // 기존 DB 컬럼의 최대 길이와 필수 입력 조건을 맞춘다.
    @Column(name = "userpass", length = 255, nullable = false)
    private String userPass;

    // 회원가입 시 입력받은 값으로 회원 객체를 만든다.
    public User(String userId, String userPass) {
        this.userId = userId;
        this.userPass = userPass;
    }
}