package com.conversesphere.model;

import java.io.Serializable;
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
@Data
public class Tweet{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	private String content;
	private String img;
	private String video;

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
}
