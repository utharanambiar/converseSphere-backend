package com.conversesphere.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
public class Tweet{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	private String content;
	private String img;
	private String video;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public List<Likes> getLikes() {
		return likes;
	}
	public void setLikes(List<Likes> likes) {
		this.likes = likes;
	}
	public List<Tweet> getReplyTweets() {
		return replyTweets;
	}
	public void setReplyTweets(List<Tweet> replyTweets) {
		this.replyTweets = replyTweets;
	}
	public List<User> getReTweet() {
		return reTweet;
	}
	public void setReTweet(List<User> reTweet) {
		this.reTweet = reTweet;
	}
	public Tweet getReplyFor() {
		return replyFor;
	}
	public void setReplyFor(Tweet replyFor) {
		this.replyFor = replyFor;
	}
	public Boolean getIsReply() {
		return isReply;
	}
	public void setIsReply(Boolean isReply) {
		this.isReply = isReply;
	}
	public Boolean getIsTweet() {
		return isTweet;
	}
	public void setIsTweet(Boolean isTweet) {
		this.isTweet = isTweet;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
	private List<Likes> likes = new ArrayList<>();

	@OneToMany
	private List<Tweet> replyTweets = new ArrayList<>();

	@ManyToMany
	private List<User> reTweet = new ArrayList<>();
	
	//why not one to one?
	@ManyToOne
	private Tweet replyFor;
	
	private Boolean isReply;
	private Boolean isTweet;
	private LocalDateTime createdAt;
}
