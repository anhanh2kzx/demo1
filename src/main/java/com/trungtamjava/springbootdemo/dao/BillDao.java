package com.trungtamjava.springbootdemo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trungtamjava.springbootdemo.entity.Bill;

public interface BillDao extends JpaRepository<Bill, Long> {
	@Query("select b from Bill b join b.buyer u where u.id =:buyerId")
	List<Bill> searchById(@Param("buyerId") long buyerId, Pageable pageable);
}
