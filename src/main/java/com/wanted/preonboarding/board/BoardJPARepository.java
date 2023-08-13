package com.wanted.preonboarding.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJPARepository extends JpaRepository<Board, Integer> {
    Page<Board> findAll(Pageable pageable);
}
