package com.cos.blog.repisitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

// 자동으로 Bean으로 등록이 된다. (@Repository 생략 가능)
public interface UserRepository extends JpaRepository<User ,Integer>{
	// SELECT * FROM user WHERE username = 1?;
	Optional<User> findByUsername(String username);
}

 

//JPA Naming 쿼리
	// SELECT * FROM user WHERE username = ?1 AND password = ?2;
	//User findByUsernameAndPassword(String username, String password);
	
	
	//두번째 방법.
	//@Query(value ="SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
	//User login(String username, String password);
