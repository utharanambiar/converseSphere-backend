package com.conversesphere.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	private String otp;
	private Boolean isVerified = false;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Tweet> tweet = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Likes> likes = new ArrayList<>();
	
	@Embedded
	private Verification verification;
	
	@JsonIgnore
	@ManyToMany
	private List<User> followers = new ArrayList<>();
	
	@JsonIgnore
	@ManyToMany
	private List<User> following = new ArrayList<>();

}
