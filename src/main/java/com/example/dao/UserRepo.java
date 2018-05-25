package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.User;


@Repository
public interface UserRepo extends CrudRepository<User, String> {
	@Query("from User")
    List<User> findAll();
	
	@Query("from User where id = ?1")
	User findUserById(String id);
}
