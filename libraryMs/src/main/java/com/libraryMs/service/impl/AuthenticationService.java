package com.libraryMs.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.libraryMs.model.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.libraryMs.dto.AuthRequest;
import com.libraryMs.dto.AuthRequestRegister;
import com.libraryMs.dto.AuthResponse;
import com.libraryMs.dto.DtoUser;
import com.libraryMs.dto.RefreshTokenRequest;
import com.libraryMs.enums.MessageType;
import com.libraryMs.exception.BaseException;
import com.libraryMs.exception.ErrorMessage;
import com.libraryMs.jwt.JWTService;
import com.libraryMs.model.RefreshToken;
import com.libraryMs.repository.RefreshTokenRepository;
import com.libraryMs.repository.UserRepository;
import com.libraryMs.service.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	private Users createUserRegister(AuthRequestRegister authRequest) {
		Users users = new Users();
		users.setUsername(authRequest.getUsername());
		users.setPassword(encoder.encode(authRequest.getPassword()));
		users.setEmail(authRequest.getEmail());
		users.setRole(authRequest.getRole());

		return users;
	}

	private RefreshToken createRefreshToken(Users users) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setUsers(users);

		return refreshToken;
	}

	@Override
	public DtoUser register(AuthRequestRegister input) {
		DtoUser dtoUser = new DtoUser();
		Users savedUsers = userRepository.save(createUserRegister(input));

		BeanUtils.copyProperties(savedUsers, dtoUser);
		return dtoUser;
	}

	@Override
	public AuthResponse authenticate(AuthRequest input) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					input.getUsername(), input.getPassword());
			authenticationProvider.authenticate(authenticationToken);

			Optional<Users> optional = userRepository.findByUsername(input.getUsername());

			String accessToken = jwtService.generateToken(optional.get());
			RefreshToken refreshToken = createRefreshToken(optional.get());

			RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);

			return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());

		} catch (Exception e) {
			throw new BaseException(new ErrorMessage(e.getMessage(), MessageType.USERNAME_OR_PASSWORD_INVALID));
		}
	}

	public boolean isValidRefreshToken(Date expiredDate) {

		return new Date().before(expiredDate);
	}

	@Override
	public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		Optional<RefreshToken> optRefreshToken = refreshTokenRepository
				.findByRefreshToken(refreshTokenRequest.getRefreshToken());

		if (optRefreshToken.isEmpty()) {
			throw new BaseException(
					new ErrorMessage(refreshTokenRequest.getRefreshToken(), MessageType.NO_RECORD_EXIST));
		}

		if (!isValidRefreshToken(optRefreshToken.get().getExpiredDate())) {
			throw new BaseException(
					new ErrorMessage(refreshTokenRequest.getRefreshToken(), MessageType.REFRESH_TOKEN_IS_EXPIRED));
		}

		Users users = optRefreshToken.get().getUsers();
		String accessToken = jwtService.generateToken(users);
		RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(users));

		return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
	}
}
