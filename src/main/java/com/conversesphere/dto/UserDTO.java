package com.conversesphere.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;
	private String fullName;
	private String userName;
	private String location;
	private String website;
	private String DOB;
	private String email;
	private String password;
	private String phoneNumber;
	private String profileImage;
	private String bannerImage;
	private String bio;
	private Boolean isReqUser;
	
	private Boolean isLoggedInWith3P = false;
	
	private List<UserDTO> followers = new ArrayList<>();
	
	private List<UserDTO> following = new ArrayList<>();
	
	private boolean followed;
	
	private boolean isVerified;
}
