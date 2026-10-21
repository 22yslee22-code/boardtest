package com.board2.boardtest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "userPass") // 비번 출력 제외
public class UserDTO {

    private String userId;
    private String userPass;

}
