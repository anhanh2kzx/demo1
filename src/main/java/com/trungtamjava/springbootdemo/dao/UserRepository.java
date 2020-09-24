package com.trungtamjava.springbootdemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trungtamjava.springbootdemo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.username like :uname")
	User getByUsername(@Param("uname") String username);// duy nhat

	@Query("SELECT u FROM User u WHERE u.name LIKE :name")
	List<User> search(@Param("name") String name);
}
