package com.conversesphere.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversesphere.dto.TweetDTO;
import com.conversesphere.exception.TweetException;
import com.conversesphere.exception.UserException;
import com.conversesphere.mapper.TweetDTOMapper;
import com.conversesphere.model.Tweet;
import com.conversesphere.model.User;
import com.conversesphere.request.TweetReplyRequest;
import com.conversesphere.response.APIResponse;
import com.conversesphere.service.TweetService;
import com.conversesphere.service.UserService;

@RestController
@RequestMapping("/api/tweet")
public class TweetController {

	private UserService userService;

	private TweetService tweetService;

	@PostMapping("/create")
	public ResponseEntity<TweetDTO> createTweet(@RequestBody Tweet req, @RequestHeader("Authorization") String jwt)
			throws UserException, TweetException {
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.createTweet(req, user);
		TweetDTO tweetDTO = TweetDTOMapper.toTweetDTO(tweet, user);
		return new ResponseEntity<>(tweetDTO, HttpStatus.CREATED);
	}

	@PostMapping("/reply")
	public ResponseEntity<TweetDTO> replyTweet(@RequestBody TweetReplyRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException {
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.createReply(req, user);
		TweetDTO tweetDTO = TweetDTOMapper.toTweetDTO(tweet, user);
		return new ResponseEntity<>(tweetDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{tweetId}/retweet")
	public ResponseEntity<TweetDTO> reTweet(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt)
			throws UserException, TweetException {
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.retweet(tweetId, user);
		TweetDTO tweetDTO = TweetDTOMapper.toTweetDTO(tweet, user);
		return new ResponseEntity<>(tweetDTO, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{tweetId}")
	public ResponseEntity<TweetDTO> findTweetById(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException {
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.findById(tweetId);
		TweetDTO tweetDTO = TweetDTOMapper.toTweetDTO(tweet, user);
		return new ResponseEntity<>(tweetDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{tweetId}")
	public ResponseEntity<APIResponse> deleteTweetById(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException {
		User user = userService.findUserProfileByJwt(jwt);
		tweetService.deleteTweetById(tweetId, user.getId());
		APIResponse res = new APIResponse("Tweet deleted successfully!", true);
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}

	@GetMapping("/")
	public ResponseEntity<List<TweetDTO>> getAllTweets(@RequestHeader("Authorization") String jwt)
			throws UserException, TweetException {
		User user = userService.findUserProfileByJwt(jwt);
		List<Tweet> tweets = tweetService.findAllTweets();
		List<TweetDTO> tweetDTOs = TweetDTOMapper.toTweetDTOs(tweets, user);
		return new ResponseEntity<>(tweetDTOs, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TweetDTO>> getAllTweetsForUser(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException {
		User user = userService.findUserProfileByJwt(jwt);
		List<Tweet> tweets = tweetService.getUserTweets(user);
		List<TweetDTO> tweetDTOs = TweetDTOMapper.toTweetDTOs(tweets, user);
		return new ResponseEntity<>(tweetDTOs, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/likes")
	public ResponseEntity<List<TweetDTO>> findTweetsLikedByUser(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException {
		User user = userService.findUserProfileByJwt(jwt);
		List<Tweet> tweets = tweetService.getLikesByUser(user);
		List<TweetDTO> tweetDTOs = TweetDTOMapper.toTweetDTOs(tweets, user);
		return new ResponseEntity<>(tweetDTOs, HttpStatus.OK);
	}
}
