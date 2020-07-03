package com.example.heroku.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.heroku.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

	@Query("select u from User u where u.email=:email")
	User findByEmail(@Param("email") String email);

	@Query("select u from User u where u.token=:token")
	User findByToken(@Param("token") String token);
}
