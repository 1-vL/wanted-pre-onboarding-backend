package com.wanted.preonboarding.board;

import com.wanted.preonboarding._core.security.CustomUserDetails;
import com.wanted.preonboarding._core.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardService boardService;

    @PostMapping("/boards/post")
    public ResponseEntity<?> post(@RequestBody @Valid BoardRequest.PostDTO postDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        boardService.post(postDTO, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<?> getOne(@PathVariable("boardId") int boardId) {
        BoardResponse.FindById boardDetail = boardService.getOne(boardId);
        return ResponseEntity.ok().body(ApiUtils.success(boardDetail));
    }

    @GetMapping("/boards")
    public ResponseEntity<?> getAll(final Pageable pageable) {
        BoardResponse.ListDTO boardList = boardService.getAll(pageable);
        return ResponseEntity.ok().body(ApiUtils.success(boardList));
    }

    @PutMapping("/boards")
    public ResponseEntity<?> edit(@RequestBody @Valid BoardRequest.EditDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        BoardResponse.EditById editedBoard = boardService.edit(requestDTO, userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(editedBoard));
    }
    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<?> delete(@PathVariable("boardId") int boardId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        BoardResponse.DeleteById deletedBoard = boardService.delete(boardId, userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(deletedBoard));
    }

}
