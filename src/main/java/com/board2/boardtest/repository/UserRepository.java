package com.board2.boardtest.repository;

import com.board2.boardtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// User Entity를 관리하며, 기본키인 userId의 Java 타입은 String이다.
public interface UserRepository extends JpaRepository<User, String> {
}