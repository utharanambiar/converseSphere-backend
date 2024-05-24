package com.conversesphere.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class TweetDTO {

	private Long id;

	private String content;

	private String img;

	private String video;

	private UserDTO user;

	private LocalDateTime createdAt;

	private int totalLikes;

	private int totalReplies;

	private int totalRetweets;

	private boolean isLiked;

	private boolean isRetweet;

	private List<Long> retweetUserIds;

	private List<TweetDTO> replyTweets;
}
