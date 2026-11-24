package com.board2.boardtest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_table", schema = "public")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    // PostgreSQL의 identity 컬럼이 글 번호를 자동 생성한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "boardtitle", length = 50)
    private String boardTitle;

    @Column(name = "boardwriter", length = 20)
    private String boardWriter;

    @Column(name = "boardpass", length = 20)
    private String boardPass;

    @Column(name = "boardcontents", length = 500)
    private String boardContents;

    // 기존 데이터에 NULL이 있을 수 있으므로 Integer를 사용한다.
    @Column(name = "boardhits")
    private Integer boardHits;

    // 생성 시에는 DB의 now() 기본값을 사용하고, 수정 시에도 변경하지 않는다.
    @Column(name = "createdat", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "fileattached")
    private Integer fileAttached;

    // 새 게시글에 필요한 값과 초기값을 설정한다.
    public Board(String boardTitle, String boardWriter, String boardContents) {
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardContents = boardContents;
        this.boardHits = 0;
        this.fileAttached = 0;
    }

    // 기존 수정 기능과 동일하게 제목과 내용만 변경한다.
    public void update(String boardTitle, String boardContents) {
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
    }
}