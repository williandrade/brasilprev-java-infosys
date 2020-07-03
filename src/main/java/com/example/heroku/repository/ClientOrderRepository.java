package com.example.heroku.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.heroku.entity.ClientOrder;

public interface ClientOrderRepository
		extends JpaRepository<ClientOrder, Integer>, PagingAndSortingRepository<ClientOrder, Integer> {

	@Query("select c from ClientOrder c where c.clientId=:id")
	List<ClientOrder> findAllByClientId(@Param("id") Integer id);
}
