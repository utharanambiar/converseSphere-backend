package com.conversesphere.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conversesphere.config.JwtProvider;
import com.conversesphere.exception.UserException;
import com.conversesphere.model.User;
import com.conversesphere.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserById(Long userId) throws UserException {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new UserException("User not found with this ID"));
		return user;
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		// TODO Auto-generated method stub
		String email = jwtProvider.getEmailFromToken(jwt);
		User user = userRepo.findByEmail(email);
		if (user == null)
			throw new UserException("User not found with email" + email);
		return user;
	}

	@Override
	public User updateUser(Long userId, User user) throws UserException {
		// TODO Auto-generated method stub
		User userFound = findUserById(userId);

		if (user.getFullName() != null) {
			userFound.setFullName(user.getFullName());
		}
		if (user.getBio() != null) {
			userFound.setBio(user.getBio());
		}
		if (user.getBannerImage() != null) {
			userFound.setBannerImage(user.getBannerImage());
		}
		if (user.getProfileImage() != null) {
			userFound.setProfileImage(user.getProfileImage());
		}
		if (user.getDOB() != null) {
			userFound.setDOB(user.getDOB());
		}
		if (user.getLocation() != null) {
			userFound.setLocation(user.getLocation());
		}
		if (user.getWebsite() != null) {
			userFound.setWebsite(user.getWebsite());
		}
		return userRepo.save(userFound);
	}

	@Override
	public User followUser(Long userId, User user) throws UserException {
		// TODO Auto-generated method stub
		User userToBeFollowed = findUserById(userId);
		if (user.getFollowing().contains(userToBeFollowed) && userToBeFollowed.getFollowers().contains(user)) {
			user.getFollowing().remove(userToBeFollowed);
			userToBeFollowed.getFollowers().remove(user);
		} else {
			user.getFollowing().add(userToBeFollowed);
			userToBeFollowed.getFollowers().add(user);
		}
		userRepo.save(userToBeFollowed);
		userRepo.save(user);

		return userToBeFollowed;
	}

	@Override
	public List<User> searchUser(String query) {
		// TODO Auto-generated method stub
		return userRepo.searchUser(query);
	}
	
	@Override
	public User updateUserOtp(Long userId, User user) throws UserException {
		// TODO Auto-generated method stub
		User userFound = findUserById(userId);

		if (user.getOtp() != null) {
			userFound.setOtp(user.getOtp());
		}
		return userRepo.save(userFound);
	}

}
