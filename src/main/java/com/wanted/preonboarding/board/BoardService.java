package com.wanted.preonboarding.board;

import com.wanted.preonboarding._core.errors.exception.Exception400;
import com.wanted.preonboarding._core.errors.exception.Exception401;
import com.wanted.preonboarding._core.errors.exception.Exception500;
import com.wanted.preonboarding.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJPARepository boardJPARepository;

    @Transactional
    public void post(BoardRequest.PostDTO requestDTO, User author) {
        requestDTO.setAuthor(author);
        try {
            boardJPARepository.save(requestDTO.toEntity());
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

    public BoardResponse.FindById getOne(int boardId) {
        Board boardPS = boardJPARepository.findById(boardId).orElseThrow(
                () -> new Exception400("게시물 번호를 찾을 수 없습니다 : "+boardId)
        );
        return new BoardResponse.FindById(boardPS);
    }

    public BoardResponse.ListDTO getAll(final Pageable pageable) {
        Page<Board> boardPage = boardJPARepository.findAll(pageable);
        List<Board> boardList = boardPage.stream().toList();
        return new BoardResponse.ListDTO(boardList);
    }

    @Transactional
    public BoardResponse.EditById edit(BoardRequest.EditDTO requestDTO, User author) {
        checkExistAndAuth(requestDTO.getId(), author);
        requestDTO.setAuthor(author);
        Board editedBoard = boardJPARepository.save(requestDTO.toEntity());
        return new BoardResponse.EditById(editedBoard);
    }

    @Transactional
    public BoardResponse.DeleteById delete(int boardId, User author) {
        Board originalBoard = checkExistAndAuth(boardId, author);
        boardJPARepository.delete(originalBoard);
        return new BoardResponse.DeleteById(originalBoard);
    }

    private Board checkExistAndAuth(int boardId, User author) {
        Board originalBoard = boardJPARepository.findById(boardId).orElseThrow(
                () -> new Exception400("게시물 번호를 찾을 수 없습니다 : "+boardId)
        );
        boolean isOwner = originalBoard.checkOwner(author);
        if (!isOwner) throw new Exception401("이 게시글의 작성자가 아닙니다 : "+boardId);
        return originalBoard;
    }
}
