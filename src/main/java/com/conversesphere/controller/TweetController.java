package com.conversesphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversesphere.dto.TweetDTO;
import com.conversesphere.service.TweetService;
import com.conversesphere.service.UserService;

@RestController
@RequestMapping("/api/tweet")
public class TweetController {
	
	private UserService userService;
	
	private TweetService tweetService;
	
	public ResponseEntity<TweetDTO> tweetDTO;

}
