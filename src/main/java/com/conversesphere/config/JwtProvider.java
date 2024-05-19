package com.conversesphere.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

	//SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());
	SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	

	public String generateToken(Authentication auth) {
		String jwt = Jwts.builder().issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 86400000))
				.claim("email", auth.getName()).signWith(key).compact();
		return jwt;
	}
	
	public String getEmailFromToken(String jwt) {
		jwt = jwt.substring(7);
		Claims claims = Jwts.parser().verifyWith(key).build().parseUnsecuredClaims(jwt).getPayload();
		
		String email = String.valueOf(claims.get("email"));
		return email;
	}

}
