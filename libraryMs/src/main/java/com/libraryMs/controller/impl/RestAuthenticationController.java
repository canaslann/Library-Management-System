package com.libraryMs.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.libraryMs.controller.IRestAuthenticationController;
import com.libraryMs.controller.RestBaseController;
import com.libraryMs.controller.RootEntity;
import com.libraryMs.dto.AuthRequest;
import com.libraryMs.dto.AuthRequestRegister;
import com.libraryMs.dto.AuthResponse;
import com.libraryMs.dto.DtoUser;
import com.libraryMs.dto.RefreshTokenRequest;
import com.libraryMs.service.IAuthenticationService;

import jakarta.validation.Valid;

@RestController
public class RestAuthenticationController extends RestBaseController implements IRestAuthenticationController {

	@Autowired
	private IAuthenticationService authenticationService;

	@PostMapping("/register")
	@Override
	public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequestRegister input) {

		return ok(authenticationService.register(input));
	}

	@PostMapping("/authenticate")
	@Override
	public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest input) {

		return ok(authenticationService.authenticate(input));
	}

	@PostMapping("/refreshToken")
	@Override
	public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {

		return ok(authenticationService.refreshToken(refreshTokenRequest));
	}

}
