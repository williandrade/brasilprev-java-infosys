package com.example.heroku.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.heroku.entity.ProductOrder;

public interface ProductOrderRepository
		extends JpaRepository<ProductOrder, Integer>, PagingAndSortingRepository<ProductOrder, Integer> {

	@Query("select po from ProductOrder po where po.orderId=:id")
	List<ProductOrder> findAllByOrderId(@Param("id") Integer id);

}