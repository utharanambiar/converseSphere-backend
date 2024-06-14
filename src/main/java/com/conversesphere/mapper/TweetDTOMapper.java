package com.conversesphere.mapper;

import java.util.ArrayList;
import java.util.List;

import com.conversesphere.dto.TweetDTO;
import com.conversesphere.dto.UserDTO;
import com.conversesphere.model.Tweet;
import com.conversesphere.model.User;
import com.conversesphere.util.TweetUtil;

public class TweetDTOMapper {

	public static TweetDTO toTweetDTO(Tweet tweet, User reqUser) {
		UserDTO user = UserDTOMapper.toUserDTO(tweet.getUser());

		boolean isLiked = TweetUtil.isLikedByReqUser(reqUser, tweet);

		boolean isRetweeted = TweetUtil.isRetweetedByReqUser(reqUser, tweet);

		List<Long> retweetUserIds = new ArrayList<>();

		for (User userRetweet : tweet.getReTweetUser()) {
			retweetUserIds.add(userRetweet.getId());
		}

		TweetDTO tweetDTO = new TweetDTO();
		tweetDTO.setId(tweet.getId());
		tweetDTO.setContent(tweet.getContent());
		tweetDTO.setImg(tweet.getImg());
		tweetDTO.setVideo(tweet.getVideo());
		tweetDTO.setCreatedAt(tweet.getCreatedAt());
		tweetDTO.setTotalLikes(tweet.getLikes().size());
		tweetDTO.setTotalReplies(tweet.getReplyTweets().size());
		tweetDTO.setTotalRetweets(tweet.getReTweetUser().size());
		tweetDTO.setUser(user);
		tweetDTO.setLiked(isLiked);
		tweetDTO.setRetweet(isRetweeted);
		tweetDTO.setRetweetUserIds(retweetUserIds);
		tweetDTO.setReplyTweets(toTweetDTOs(tweet.getReplyTweets(), reqUser));
		return tweetDTO;
	}

	public static List<TweetDTO> toTweetDTOs(List<Tweet> tweets, User reqUser) {
		List<TweetDTO> tweetDTOs = new ArrayList<>();

		for (Tweet tweet : tweets) {
			TweetDTO tweetDTO = toReplyTweetDTO(tweet, reqUser);
			tweetDTOs.add(tweetDTO);
		}
		return tweetDTOs;
	}

	private static TweetDTO toReplyTweetDTO(Tweet tweet, User reqUser) {
		UserDTO user = UserDTOMapper.toUserDTO(tweet.getUser());

		boolean isLiked = TweetUtil.isLikedByReqUser(reqUser, tweet);

		boolean isRetweeted = TweetUtil.isRetweetedByReqUser(reqUser, tweet);

		List<Long> retweetUserIds = new ArrayList<>();

		for (User userRetweet : tweet.getReTweetUser()) {
			retweetUserIds.add(userRetweet.getId());
		}

		TweetDTO tweetDTO = new TweetDTO();
		tweetDTO.setId(tweet.getId());
		tweetDTO.setContent(tweet.getContent());
		tweetDTO.setImg(tweet.getImg());
		tweetDTO.setVideo(tweet.getVideo());
		tweetDTO.setCreatedAt(tweet.getCreatedAt());
		tweetDTO.setTotalLikes(tweet.getLikes().size());
		tweetDTO.setTotalReplies(tweet.getReplyTweets().size());
		tweetDTO.setTotalRetweets(tweet.getReTweetUser().size());
		tweetDTO.setUser(user);
		//tweetDTO.setReplyTweets(toTweetDTOs(tweet.getReplyTweets(), reqUser));
		tweetDTO.setLiked(isLiked);
		tweetDTO.setRetweet(isRetweeted);
		tweetDTO.setRetweetUserIds(retweetUserIds);
		return tweetDTO;
	}
}
