package com.Payvang.Login.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	private static final String SECRET_KEY = "3f7d1b8a7d8e4d2c8f9b21a3e4f6a7b9c1e5d7f9b1c6a8e4d7f9b3a6d1e7c9a8";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY).build();
		return parser.parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 5)) // 5 minute token expiry
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, String username) {
		try {
			final String extractedUsername = extractUsername(token);
			return (extractedUsername.equals(username) && !isTokenExpired(token));
		} catch (ExpiredJwtException e) {
			return false;
		}
	}

	// New method to verify if the token is expired or not
	public Boolean isTokenExpiredOrNot(String token) {
		try {
			return !isTokenExpired(token); // Returns true if the token is not expired, false otherwise
		} catch (ExpiredJwtException e) {
			return false; // If an exception is thrown, consider the token as expired
		}
	}

}


