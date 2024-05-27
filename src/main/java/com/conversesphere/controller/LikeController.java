package com.conversesphere.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversesphere.dto.LikeDTO;
import com.conversesphere.exception.TweetException;
import com.conversesphere.exception.UserException;
import com.conversesphere.mapper.LikeDTOMapper;
import com.conversesphere.model.Likes;
import com.conversesphere.model.User;
import com.conversesphere.service.LikeService;
import com.conversesphere.service.UserService;

@RestController
@RequestMapping("/api")
public class LikeController {

	@Autowired
	private UserService userService;

	@Autowired
	private LikeService likeService;

	@PostMapping("/{tweetId}/likes")
	public ResponseEntity<LikeDTO> likeTweet(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt)
			throws UserException, TweetException {

		User user = userService.findUserProfileByJwt(jwt);
		Likes like = likeService.likeTweet(tweetId, user);
		LikeDTO likeDTO = LikeDTOMapper.toLikeDTO(like, user);
		return new ResponseEntity<LikeDTO>(likeDTO, HttpStatus.CREATED);
	}

	@PostMapping("/tweet/{tweetId}")
	public ResponseEntity<List<LikeDTO>> getAllLikes(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException {

		User user = userService.findUserProfileByJwt(jwt);
		List<Likes> likes = likeService.getAllLikes(tweetId);
		List<LikeDTO> likeDTO = LikeDTOMapper.toLikeDTOs(likes, user);
		return new ResponseEntity<List<LikeDTO>>(likeDTO, HttpStatus.OK);
	}

}
