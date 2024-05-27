package com.conversesphere.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conversesphere.exception.TweetException;
import com.conversesphere.exception.UserException;
import com.conversesphere.model.Likes;
import com.conversesphere.model.Tweet;
import com.conversesphere.model.User;
import com.conversesphere.repository.LikeRepository;
import com.conversesphere.repository.TweetRepository;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	private LikeRepository likeRepo;

	@Autowired
	private TweetService tweetService;

	@Autowired
	private TweetRepository tweetRepo;

	@Override
	public Likes likeTweet(Long tweetId, User user) throws UserException, TweetException {
		// TODO Auto-generated method stub
		Likes doesLikeExist = likeRepo.doesLikeExist(user.getId(), tweetId);
		if (doesLikeExist != null) {
			likeRepo.deleteById(doesLikeExist.getId());
			return doesLikeExist;
		}

		Tweet tweet = tweetService.findById(tweetId);
		Likes like = new Likes();
		like.setTweet(tweet);
		like.setUser(user);

		Likes savedLike = likeRepo.save(like);
		return savedLike;
	}

	@Override
	public List<Likes> getAllLikes(Long tweetId) throws TweetException {
		// TODO Auto-generated method stub
		Tweet tweet = tweetService.findById(tweetId);
		List<Likes> likes = likeRepo.findByTweetId(tweet.getId());
		return likes;
	}

}
