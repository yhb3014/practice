package com.cos.blog.repisitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board ,Integer>{
	
	Page<Board> findByTitleContaining(String keyword, Pageable pageable);
	@Modifying
    @Query(value = "UPDATE Board p set p.count = p.count + 1 where p.id = :id", nativeQuery = true)
    int updateView(int id);
}
