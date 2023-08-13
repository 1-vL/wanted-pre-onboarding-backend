package com.wanted.preonboarding.board;

import com.wanted.preonboarding.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="board_tb")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 4000, nullable = false)
    private String content;

    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @Builder
    public Board(int id, String title, String content, User author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public boolean checkOwner(User user) {
        return this.author.getId() == user.getId();
    }

}
