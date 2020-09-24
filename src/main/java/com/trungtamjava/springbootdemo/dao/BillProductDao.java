package com.trungtamjava.springbootdemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trungtamjava.springbootdemo.entity.BillProduct;

public interface BillProductDao extends JpaRepository<BillProduct, Long> {
@Query("select bp from BillProduct bp join bp.product p join bp.bill b where b.id =:billId")
	List<BillProduct> searchbyBill(@Param("billId") Long billId);
}
