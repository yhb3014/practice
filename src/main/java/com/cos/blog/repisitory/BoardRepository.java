package com.cos.blog.repisitory;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board ,Integer>{
}
