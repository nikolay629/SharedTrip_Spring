package com.sharedTrip.repositories;

import java.util.TreeSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sharedTrip.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("Select u from User u where u.username = ?1")
	User findByUsername(String username);

	@Query("Select u.username from User u")
	TreeSet<String> findAllUsername();
}
