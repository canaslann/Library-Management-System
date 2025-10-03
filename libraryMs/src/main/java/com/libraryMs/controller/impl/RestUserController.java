package com.libraryMs.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libraryMs.controller.IRestUserController;
import com.libraryMs.controller.RestBaseController;
import com.libraryMs.controller.RootEntity;
import com.libraryMs.dto.DtoUser;
import com.libraryMs.dto.DtoUserIU;
import com.libraryMs.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/user")
public class RestUserController extends RestBaseController implements IRestUserController {

	@Autowired
	private IUserService userService;

	@GetMapping("/getAllUsers")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public List<DtoUser> getAllUsers() {

		return userService.getAllUsers();
	}

	@GetMapping("/getUserById/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public RootEntity<DtoUser> getUserById(@PathVariable Long id) {

		return ok(userService.getUserById(id));
	}

	@PutMapping("/updateUser/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public RootEntity<DtoUser> updateUser(@PathVariable Long id, @RequestBody @Valid DtoUserIU input) {

		return ok(userService.updateUser(id, input));
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public String deleteUser(@PathVariable Long id) {

		return userService.deleteUser(id);
	}

	@PutMapping("/changePassword/{id}")
	@PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
	@Override
	public String changePassword(@PathVariable Long id, @RequestBody String newPassword) {

		return userService.changePassword(id, newPassword);
	}

}
