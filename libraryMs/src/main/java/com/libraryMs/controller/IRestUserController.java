package com.libraryMs.controller;

import java.util.List;

import com.libraryMs.dto.DtoUser;
import com.libraryMs.dto.DtoUserIU;

public interface IRestUserController {

	public List<DtoUser> getAllUsers();

	public RootEntity<DtoUser> getUserById(Long id);

	public RootEntity<DtoUser> updateUser(Long id, DtoUserIU input);

	public String deleteUser(Long id);

	public String changePassword(Long id, String newPassword);

}
