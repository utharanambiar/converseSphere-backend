package com.conversesphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.conversesphere.model.Likes;

public interface LikeRepository extends JpaRepository<Likes, Long>{
	
	@Query("SELECT L FROM Likes L WHERE L.user.id=:userId AND L.tweet.id=:tweetId")
	public Likes doesLikeExist(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

	@Query("SELECT COUNT(*) FROM Likes L WHERE tweet.id=:tweetId")
	public List<Likes> findByTweetId(@Param("tweetId") Long tweetId);
}
