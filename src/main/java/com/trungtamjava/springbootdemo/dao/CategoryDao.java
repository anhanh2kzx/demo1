package com.trungtamjava.springbootdemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trungtamjava.springbootdemo.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {

	@Query("SELECT c FROM Category c WHERE c.name LIKE :name")
	List<Category> search(@Param("name") String name);
}
