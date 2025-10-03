package com.libraryMs.service;

import java.util.List;

import com.libraryMs.dto.DtoUser;
import com.libraryMs.dto.DtoUserIU;

public interface IUserService {

	public List<DtoUser> getAllUsers();

	public DtoUser getUserById(Long id);

	public DtoUser updateUser(Long id, DtoUserIU input);

	public String deleteUser(Long id);

	public String changePassword(Long id, String newPassword);
}
