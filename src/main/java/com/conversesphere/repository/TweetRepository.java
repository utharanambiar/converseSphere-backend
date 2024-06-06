package com.conversesphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.conversesphere.model.Tweet;
import com.conversesphere.model.User;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

	public List<Tweet> findAllByIsTweetTrueOrderByCreatedAtDesc();
	
	public List<Tweet> findByReTweetContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(User user, Long Id);
	
	public List<Tweet> findByUser_IdAndIsReplyTrueOrderByCreatedAtDesc(Long userId);
	
	@Query("SELECT t FROM Tweet t JOIN t.likes l WHERE l.user.id=:userId")
	public List<Tweet> findByLikesUser_Id(Long userId);
}
