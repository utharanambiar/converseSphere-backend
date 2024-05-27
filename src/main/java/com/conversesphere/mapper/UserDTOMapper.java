package com.conversesphere.mapper;

import java.util.ArrayList;
import java.util.List;

import com.conversesphere.dto.UserDTO;
import com.conversesphere.model.User;

public class UserDTOMapper {

	public static UserDTO toUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setFullName(user.getFullName());
		userDTO.setUserName(user.getUserName());
		userDTO.setBio(user.getBio());
		userDTO.setBannerImage(user.getBannerImage());
		userDTO.setDOB(user.getDOB());
		userDTO.setLocation(user.getLocation());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setProfileImage(user.getProfileImage());
		userDTO.setFollowers(toUserDTOs(user.getFollowers()));
		userDTO.setFollowing(toUserDTOs(user.getFollowing()));
		userDTO.setIsLoggedInWith3P(user.getIsLoggedInWith3P());
		// userDTO.setVerified(user.getVerification());
		return userDTO;
	}

	public static List<UserDTO> toUserDTOs(List<User> followers) {
		// TODO Auto-generated method stub
		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : followers) {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setEmail(user.getEmail());
			userDTO.setFullName(user.getFullName());
			userDTO.setProfileImage(user.getProfileImage());
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

}
