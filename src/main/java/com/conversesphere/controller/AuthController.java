package com.conversesphere.controller;

import java.util.Random;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conversesphere.config.JwtProvider;
import com.conversesphere.dto.UserDTO;
import com.conversesphere.exception.UserException;
import com.conversesphere.mapper.UserDTOMapper;
import com.conversesphere.model.User;
import com.conversesphere.model.Verification;
import com.conversesphere.repository.UserRepository;
import com.conversesphere.response.APIResponse;
import com.conversesphere.response.AuthResponse;
import com.conversesphere.service.EmailService;
import com.conversesphere.service.UserDetailsServiceImpl;
import com.conversesphere.service.UserService;

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

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUser(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFullName();
		String DOB = user.getDOB();

		if (userRepo.findByEmail(email) != null) {
			throw new UserException("Account exists with this email ID");
		}

		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFullName(fullName);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setDOB(DOB);
		createdUser.setVerification(new Verification());


		User savedUser = userRepo.save(createdUser);
		if (!email.equals("dummyaccount@example.com")) {
			String otp = generateOTP();
			createdUser.setOtp(otp);
			sendVerificationEmail(email, otp);
		}
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtprovider.generateToken(authentication);
		AuthResponse res = new AuthResponse(token, true);

		return new ResponseEntity<AuthResponse>(res, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	ResponseEntity<AuthResponse> signInUser(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String password = user.getPassword();

		Authentication authentication = authenticate(email, password);
		String token = jwtprovider.generateToken(authentication);
		AuthResponse res = new AuthResponse(token, true);
		String otp = generateOTP();
		User user1 = userRepo.findByEmail(email);
		if (!otp.equals(user1.getOtp()) && !email.equals("dummyaccount@example.com")) {
			updateOTP(user1.getId(), user1.getOtp(), otp);
			sendVerificationEmail(user1.getEmail(), otp);
		}

		return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
	}

	@PostMapping("/verify")
	ResponseEntity<Object> verifyUser(@RequestParam String email, @RequestParam String otp) {
		try {
			verifyOTP(email, otp);
			return new ResponseEntity<Object>("User verified", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/logout")
	ResponseEntity<Object> logoutUser(@RequestBody User user) {
		try {
			User user1 = userRepo.findByEmail(user.getEmail());
			user1.setIsVerified(false);
			userRepo.save(user1);
			return new ResponseEntity<Object>("User logged out", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private Authentication authenticate(String email, String password) {
		// TODO Auto-generated method stub
		UserDetails userDetails = customUserDetails.loadUserByUsername(email);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	public String generateOTP() {
		// TODO Auto-generated method stub
		Random random = new Random();
		int otpValue = 100000 + random.nextInt(900000);

		return String.valueOf(otpValue);
	}

	public void sendVerificationEmail(String email, String otp) {
		// TODO Auto-generated method stub
		String subject = "Email verification";
		String body = "Your One time password is: " + otp;
		emailService.sendMail(email, subject, body);
	}

	public void verifyOTP(String email, String otp) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("User not found");
		} else if (!otp.equals(user.getOtp())) {
			user.setIsVerified(false);
			userRepo.save(user);
			throw new RuntimeException("Invalid OTP");
		} else if (otp.equals(user.getOtp())) {
			user.setIsVerified(true);
			userRepo.save(user);
		} else {
			throw new RuntimeException("Server error");
		}
	}

	public void updateOTP(Long userId, String oldOTP, String newOtp) {
		try {
			User userFound = userService.findUserById(userId);
			if (!oldOTP.equals(newOtp)) {
				userFound.setOtp(newOtp);
				userRepo.save(userFound);
			} else {
				throw new RuntimeException("Server error 2");
			}
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
