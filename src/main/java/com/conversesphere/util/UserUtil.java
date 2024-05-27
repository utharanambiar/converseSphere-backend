package com.conversesphere.util;

import com.conversesphere.model.User;

public class UserUtil {

	public static final boolean isReqUser(User reqUser, User user) {

		return reqUser.getId().equals(user.getId());
	}

	public static final boolean isFollowedByReqUser(User reqUser, User user) {

		return reqUser.getFollowing().contains(user);
	}

}
