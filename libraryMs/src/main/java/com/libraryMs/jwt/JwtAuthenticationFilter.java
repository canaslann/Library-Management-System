package com.libraryMs.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.libraryMs.enums.MessageType;
import com.libraryMs.exception.BaseException;
import com.libraryMs.exception.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JWTService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token;
		String username;

		token = header.substring(7);
		username = jwtService.getUsernameFromToken(token);

		try {
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (userDetails != null && jwtService.isTokenValid(token)) {
					String role = jwtService.getRoleFromToken(token);

					GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
					Collection<GrantedAuthority> authorities = Collections.singletonList(authority);

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					authenticationToken.setDetails(userDetails);

					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}

			}
		} catch (ExpiredJwtException ex) {
			throw new BaseException(new ErrorMessage(ex.getMessage(), MessageType.TOKEN_IS_EXPIRED));
		} catch (Exception ex) {
			throw new BaseException(new ErrorMessage(ex.getMessage(), MessageType.GENERAL_EXCEPTION));
		}

		filterChain.doFilter(request, response);
	}
}
