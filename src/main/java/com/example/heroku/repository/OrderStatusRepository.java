package com.example.heroku.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.heroku.entity.OrderStatus;

public interface OrderStatusRepository
		extends JpaRepository<OrderStatus, Integer>, PagingAndSortingRepository<OrderStatus, Integer> {

}
