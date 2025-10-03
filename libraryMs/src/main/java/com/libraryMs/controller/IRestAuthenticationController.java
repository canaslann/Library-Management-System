package com.libraryMs.controller;

import com.libraryMs.dto.AuthRequest;
import com.libraryMs.dto.AuthRequestRegister;
import com.libraryMs.dto.AuthResponse;
import com.libraryMs.dto.DtoUser;
import com.libraryMs.dto.RefreshTokenRequest;

public interface IRestAuthenticationController {

	public RootEntity<DtoUser> register(AuthRequestRegister input);

	public RootEntity<AuthResponse> authenticate(AuthRequest input);

	public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
