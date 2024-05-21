package com.conversesphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.conversesphere.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);
	
	@Query("SELECT DISTINCT user FROM User user WHERE user.fullName LIKE %:query% OR user.email LIKE %:query%")
	public List<User> searchUser(@Param("query") String query);
}
