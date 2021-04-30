package com.cos.blog.repisitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// 자동으로 Bean으로 등록이 된다. (@Repository 생략 가능)
public interface UserRepository extends JpaRepository<User ,Integer>{

}
 