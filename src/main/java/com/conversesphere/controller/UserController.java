package com.conversesphere.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conversesphere.dto.UserDTO;
import com.conversesphere.exception.UserException;
import com.conversesphere.mapper.UserDTOMapper;
import com.conversesphere.model.User;
import com.conversesphere.service.UserService;
import com.conversesphere.util.UserUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		UserDTO userDTO = UserDTOMapper.toUserDTO(user);
		userDTO.setIsReqUser(true);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt)
			throws UserException {
		User reqUser = userService.findUserProfileByJwt(jwt);
		User user = userService.findUserById(userId);
		UserDTO userDTO = UserDTOMapper.toUserDTO(user);
		userDTO.setIsReqUser(UserUtil.isReqUser(reqUser, user));
		userDTO.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.ACCEPTED);
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserDTO>> searchUser(@RequestParam String query,
			@RequestHeader("Authorization") String jwt) throws UserException {
		User reqUser = userService.findUserProfileByJwt(jwt);
		List<User> users = userService.searchUser(query);
		List<UserDTO> userDTOs = UserDTOMapper.toUserDTOs(users);

		return new ResponseEntity<>(userDTOs, HttpStatus.ACCEPTED);
	}

	@PutMapping("/update")
	public ResponseEntity<UserDTO> updateUser(@RequestBody User req, @RequestHeader("Authorization") String jwt)
			throws UserException {
		User reqUser = userService.findUserProfileByJwt(jwt);
		User user = userService.updateUser(reqUser.getId(), req);
		UserDTO userDTO = UserDTOMapper.toUserDTO(user);

		return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{userId}/follow")
	public ResponseEntity<UserDTO> followUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt)
			throws UserException {
		User reqUser = userService.findUserProfileByJwt(jwt);
		User user = userService.followUser(userId, reqUser);
		UserDTO userDTO = UserDTOMapper.toUserDTO(user);
		userDTO.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));

		return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
	}
}
