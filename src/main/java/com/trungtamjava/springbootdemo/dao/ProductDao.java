package com.trungtamjava.springbootdemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trungtamjava.springbootdemo.entity.Product;

public interface ProductDao extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p JOIN p.category c WHERE p.name LIKE :name")
	List<Product> search(@Param("name") String name);
	
	@Query("SELECT p FROM Product p JOIN p.category c WHERE c.id = :cateId")
	List<Product> search(@Param("cateId") int cateId);
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE :name")
	List<Product> searchByName(@Param("name") String name);
	@Query("SELECT p FROM Product p join p.category c where p.id =:pId")
	Product get(@Param("pId") long pId);
}
