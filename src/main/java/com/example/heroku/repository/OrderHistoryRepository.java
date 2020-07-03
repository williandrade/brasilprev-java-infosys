package com.example.heroku.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.heroku.entity.OrderHistory;

public interface OrderHistoryRepository
		extends JpaRepository<OrderHistory, Integer>, PagingAndSortingRepository<OrderHistory, Integer> {

}
