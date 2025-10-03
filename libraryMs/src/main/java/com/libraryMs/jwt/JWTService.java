package com.libraryMs.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	public static final String SECRET_KEY = "d6GLwF/0yDIW2FwRLXpGS4CNBzvcEWtUzJolMhUybTI=";

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();

		String role = userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority)
				.orElse("USER");

		claims.put("role", role);

		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3))
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
	}

	public String getRoleFromToken(String token) {
		return exportToken(token, claims -> claims.get("role", String.class));
	}

	public <T> T exportToken(String token, Function<Claims, T> claimsFunc) {
		Claims claims = getClaimsFromToken(token);

		return claimsFunc.apply(claims);
	}

	public String getUsernameFromToken(String token) {
		return exportToken(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token) {
		Date expireDate = exportToken(token, Claims::getExpiration);
		return new Date().before(expireDate);
	}

	public Claims getClaimsFromToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();

		return claims;
	}

	public Key getKey() { // java.security nin keyi kullanilir
		byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);

		return Keys.hmacShaKeyFor(bytes);
	}
}
