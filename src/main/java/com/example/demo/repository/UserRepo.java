package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;


public interface UserRepo extends JpaRepository<User, Integer> {
	//tìm kiếm theo username
	//select user where username = ?
	User findByUsername(String username);
	
	//where name = :s
	Page<User> findByName(String s , Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.name LIKE :x")
	Page<User> searchByName(@Param("x") String s ,  Pageable pageable);
	@Query("SELECT u FROM User u WHERE u.name LIKE :x"
			+ " OR u.username LIKE :x")
	List<User> searchByNameAndUsername(@Param("x") String s);
	
	@Modifying // dùng đê thêm sửa xóa
	@Query("DELETE FROM User u WHERE u.username = :x")
	int deleteUser (@Param("x") String username);
	
	//tu gen lenh xoa
	//void deleteUsername(String usernamer);
}
