package com.conversesphere.service;

import java.util.List;

import com.conversesphere.exception.TweetException;
import com.conversesphere.exception.UserException;
import com.conversesphere.model.Tweet;
import com.conversesphere.model.User;
import com.conversesphere.request.TweetReplyRequest;

public interface TweetService {
	
	public Tweet createTweet(Tweet req, User user) throws UserException, TweetException;
	
	public List<Tweet> findAllTweets();
	
	public Tweet retweet(Long tweetId, User user) throws UserException, TweetException;
	
	public Tweet findById(Long tweetId) throws TweetException;
	
	public void deleteTweetById(Long tweetId, Long userId) throws TweetException, UserException;
	
	public Tweet createReply(TweetReplyRequest req, User user) throws TweetException;
	
	public List<Tweet> getUserTweets(User user);
	
	public List<Tweet> getLikesByUser(User user);
	
	public List<Tweet> getRepliesByUser(Long userId) throws TweetException, UserException;
}
