package com.conversesphere.util;

import com.conversesphere.model.Likes;
import com.conversesphere.model.Tweet;
import com.conversesphere.model.User;

public class TweetUtil {

	public final static boolean isLikedByReqUser(User reqUser, Tweet tweet) {
		for (Likes like : tweet.getLikes()) {
			if (like.getUser().getId() == reqUser.getId()) {
				return true;
			}
		}
		return false;
	}

	public final static boolean isRetweetedByReqUser(User reqUser, Tweet tweet) {

		for (User user : tweet.getReTweet()) {
			if (user.getId() == reqUser.getId()) {
				return true;
			}
		}
		return false;
	}
}
