package com.conversesphere.service;

import java.util.List;

import com.conversesphere.exception.TweetException;
import com.conversesphere.exception.UserException;
import com.conversesphere.model.Likes;
import com.conversesphere.model.User;

public interface LikeService {

	public Likes likeTweet(Long tweetId, User user) throws UserException, TweetException;

	public List<Likes> getAllLikes(Long tweetId) throws TweetException;

}
