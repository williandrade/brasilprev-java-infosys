package com.example.heroku.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.heroku.entity.Product;

public interface ProductRepository
		extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {

	@Query("select p from Product p where p.enable=true")
	List<Product> findAllAvailables();

}
