package com.wanted.preonboarding.board;

import com.wanted.preonboarding.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


public class BoardRequest {
    @Getter
    @Setter
    public static class PostDTO {

        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        private User author;

        public Board toEntity() {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .author(author)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class EditDTO {
        @NotNull
        private int id;

        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        private User author;
        public Board toEntity() {
            return Board.builder()
                    .id(id)
                    .title(title)
                    .content(content)
                    .author(author)
                    .build();
        }
    }
}
