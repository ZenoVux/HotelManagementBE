package com.devz.hotelmanagement.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class JwtService {
	@Value("${spring.jwt.secret}")
	private String JWT_SECRET;

	@Value("${spring.jwt.jwtExpirationInMs}")
	private int JWT_EXPIRATION_TIME_IN_MILLISECONDS; // Token live 30 phút

	public String generateToken(String userName,boolean isAdmin) {
		Map<String, Object> claims = new HashMap<>();
		return tokenCreator(claims, userName, isAdmin);
	}

	public String tokenCreator(Map<String, Object> claims, String userName,boolean isAdmin) {
		claims.put("roles", isAdmin ? "ADMIN" : "STAFF");
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME_IN_MILLISECONDS))
				.signWith(getSignedKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractUsernameFromToken(String theToken) {
		return extractClaim(theToken, Claims::getSubject);
	}

	public Date extractExpirationTimeFromToken(String theToken) {
		return extractClaim(theToken, Claims::getExpiration);
	}

	public Boolean validateToken(String theToken, UserDetails userDetails) {
		final String userName = extractUsernameFromToken(theToken);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(theToken));
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignedKey()).build().parseClaimsJws(token).getBody();

	}

	private boolean isTokenExpired(String theToken) {
		return extractExpirationTimeFromToken(theToken).before(new Date());
	}

	private Key getSignedKey() {
		byte[] keyByte = Decoders.BASE64.decode(JWT_SECRET);
		return Keys.hmacShaKeyFor(keyByte);
	}

}
