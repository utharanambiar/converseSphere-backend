package com.conversesphere.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conversesphere.exception.TweetException;
import com.conversesphere.exception.UserException;
import com.conversesphere.model.Tweet;
import com.conversesphere.model.User;
import com.conversesphere.repository.TweetRepository;
import com.conversesphere.request.TweetReplyRequest;

@Service
public class TweetServiceImpl implements TweetService{
	
	@Autowired
	private TweetRepository tweetRepo;

	@Override
	public Tweet createTweet(Tweet req, User user) throws UserException, TweetException {
		
		Tweet tweet = new Tweet();
		tweet.setContent(req.getContent());
		tweet.setCreatedAt(LocalDateTime.now());
		tweet.setImg(req.getImg());
		tweet.setIsReply(false);
		tweet.setIsTweet(true);
		tweet.setUser(user);
		tweet.setVideo(req.getVideo());
		return tweetRepo.save(tweet);
	}

	@Override
	public List<Tweet> findAllTweets() {
		return tweetRepo.findAllByIsTweetTrueOrderByCreatedAtDesc();
	}

	@Override
	public Tweet retweet(Long tweetId, User user) throws UserException, TweetException {
		// TODO Auto-generated method stub
		Tweet tweet = findById(tweetId);
		if(tweet.getReTweet().contains(user)) {
			tweet.getReTweet().remove(user);
		} else {
			tweet.getReTweet().add(user);
		}
		return tweetRepo.save(tweet);
	}

	@Override
	public Tweet findById(Long tweetId) throws TweetException {
		// TODO Auto-generated method stub
		Tweet tweet = tweetRepo.findById(tweetId).orElseThrow(()-> new TweetException("Tweet not found: " + tweetId));
		return tweet;
	}

	@Override
	public void deleteTweetById(Long tweetId, Long userId) throws TweetException, UserException {
		// TODO Auto-generated method stub
		Tweet tweet = findById(tweetId);
		if(!userId.equals(tweet.getUser().getId())) {
			throw new UserException("You cannot deleted other user's tweet");
		}
		tweetRepo.deleteById(tweetId);
	}

	@Override
	public Tweet createReply(TweetReplyRequest req, User user) throws TweetException {
		
		Tweet replyForTweet = findById(req.getTweetId());
		Tweet tweet = new Tweet();
		tweet.setContent(req.getContent());
		tweet.setCreatedAt(LocalDateTime.now());
		tweet.setImg(req.getImg());
		tweet.setIsReply(true);
		tweet.setIsTweet(false);
		tweet.setUser(user);
		tweet.setVideo(req.getVideo());
		tweet.setReplyFor(replyForTweet);
		
		Tweet savedReply = tweetRepo.save(tweet);
		//tweet.getReplyTweets().add(savedReply);
		replyForTweet.getReplyTweets().add(savedReply);
		tweetRepo.save(replyForTweet);
		return replyForTweet;
	}

	@Override
	public List<Tweet> getUserTweets(User user) {
		// TODO Auto-generated method stub
		return tweetRepo.findByReTweetContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(user, user.getId());
	}

	@Override
	public List<Tweet> getLikesByUser(User user) {
		
		return tweetRepo.findByLikesUser_Id(user.getId());
	}

	@Override
	public List<Tweet> getRepliesByUser(Long userId) throws TweetException, UserException {
		// TODO Auto-generated method stub
		return tweetRepo.findByUser_IdAndIsReplyTrueOrderByCreatedAtDesc(userId);
	}

}
