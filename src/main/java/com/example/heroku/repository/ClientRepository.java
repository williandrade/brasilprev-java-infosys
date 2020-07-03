package com.example.heroku.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.heroku.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>, PagingAndSortingRepository<Client, Integer> {

	@Query("select c from Client c where c.email=:email")
	Client findByEmail(@Param("email") String email);

}
