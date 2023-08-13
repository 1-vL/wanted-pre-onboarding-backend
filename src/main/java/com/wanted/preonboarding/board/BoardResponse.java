package com.wanted.preonboarding.board;

import com.wanted.preonboarding.user.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class BoardResponse {

    @Getter @Setter
    public static class FindById{
        private int id;
        private String title;
        private String content;
        private UserInfo author;

        public FindById(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.author = new UserInfo(board.getAuthor());
        }
    }
    @Getter
    @Setter
    public static class ListDTO {
        private List<BoardDTO> boards;

        public ListDTO(List<Board> boardList) {
            this.boards = boardList.stream().map(board -> new BoardDTO(board)).toList();
        }

        @Getter
        @Setter
        public class BoardDTO {
            private int id;
            private String title;
            private UserInfo author;

            public BoardDTO(Board board) {
                this.id = board.getId();
                this.title = board.getTitle();
                this.author = new UserInfo(board.getAuthor());
            }
        }
    }
    @Getter @Setter
    public static class EditById{
        private int id;
        private String title;
        private String content;
        private UserInfo author;

        public EditById(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.author = new UserInfo(board.getAuthor());
        }
    }
    @Getter @Setter
    public static class DeleteById{
        private int id;
        private String title;
        private UserInfo author;

        public DeleteById(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.author = new UserInfo(board.getAuthor());
        }
    }

    @Getter @Setter
    public static class UserInfo{
        private int id;
        private String email;

        public UserInfo(User author) {
            this.id = author.getId();
            this.email = author.getEmail();
        }
    }
}
