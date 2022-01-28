package com.board.domain.entity;


import lombok.*;

import javax.persistence.*;

import com.board.dto.BoardDto;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
// @Setter // 모든 세터는 엔티티 내 메소드로 처리
@Table(name="board")
@Entity
public class BoardEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public BoardEntity(String title, String content, String writer) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public static BoardEntity create(BoardDto boardDto){
        return new BoardEntity(
            boardDto.getWriter(),
            boardDto.getTitle(),
            boardDto.getContent()
        );
    }
}
