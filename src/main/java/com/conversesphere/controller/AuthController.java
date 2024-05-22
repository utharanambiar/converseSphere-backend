package com.conversesphere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversesphere.config.JwtProvider;
import com.conversesphere.exception.UserException;
import com.conversesphere.model.User;
import com.conversesphere.model.Verification;
import com.conversesphere.repository.UserRepository;
import com.conversesphere.response.AuthResponse;
import com.conversesphere.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtprovider;
	
	@Autowired
	private UserDetailsServiceImpl customUserDetails;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUser(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFullName();
		String DOB = user.getDOB();
		
		if(userRepo.findByEmail(email) != null) {
			throw new UserException("Account exists with this email ID");
		} 
		
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFullName(fullName);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setDOB(DOB);
		createdUser.setVerification(new Verification());
		
		User savedUser = userRepo.save(createdUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtprovider.generateToken(authentication);
		AuthResponse res = new AuthResponse(token, true);
		
		return new ResponseEntity<AuthResponse>(res, HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	ResponseEntity<AuthResponse> signInUser(@RequestBody User user){
		String email = user.getEmail();
		String password = user.getPassword();
		
		Authentication authentication = authenticate(email, password);
		
		String token = jwtprovider.generateToken(authentication);
		AuthResponse res = new AuthResponse(token, true);
		
		return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
	}

	private Authentication authenticate(String email, String password){
		// TODO Auto-generated method stub
		UserDetails userDetails = customUserDetails.loadUserByUsername(email);
		if(userDetails == null) {
			throw new BadCredentialsException("Invalid username");
		} 
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
