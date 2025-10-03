package com.libraryMs.service;

import com.libraryMs.dto.AuthRequest;
import com.libraryMs.dto.AuthRequestRegister;
import com.libraryMs.dto.AuthResponse;
import com.libraryMs.dto.DtoUser;
import com.libraryMs.dto.RefreshTokenRequest;

public interface IAuthenticationService {

	public DtoUser register(AuthRequestRegister input);

	public AuthResponse authenticate(AuthRequest input);

	public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
