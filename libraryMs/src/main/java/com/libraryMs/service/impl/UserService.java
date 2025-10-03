package com.libraryMs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.libraryMs.model.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.libraryMs.dto.DtoUser;
import com.libraryMs.dto.DtoUserIU;
import com.libraryMs.enums.MessageType;
import com.libraryMs.exception.BaseException;
import com.libraryMs.exception.ErrorMessage;
import com.libraryMs.repository.UserRepository;
import com.libraryMs.service.IUserService;

@Service
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public List<DtoUser> getAllUsers() {
		List<Users> dbUsers = userRepository.findAll();
		List<DtoUser> userList = new ArrayList<>();

		for (Users users : dbUsers) {
			DtoUser dtoUser = new DtoUser();
			BeanUtils.copyProperties(users, dtoUser);
			userList.add(dtoUser);
		}

		return userList;
	}

	@Override
	public DtoUser getUserById(Long id) {
		Optional<Users> optUser = userRepository.findById(id);

		if (optUser.isEmpty()) {
			throw new BaseException(new ErrorMessage(id.toString(), MessageType.NO_RECORD_EXIST));
		}

		DtoUser dtoUser = new DtoUser();
		BeanUtils.copyProperties(optUser.get(), dtoUser);

		return dtoUser;
	}

	@Override
	public DtoUser updateUser(Long id, DtoUserIU input) {
		Optional<Users> optUser = userRepository.findById(id);

		if (optUser.isEmpty()) {
			throw new BaseException(new ErrorMessage(id.toString(), MessageType.NO_RECORD_EXIST));
		}

		optUser.get().setUsername(input.getUsername());
		optUser.get().setPassword(encoder.encode(input.getPassword()));
		optUser.get().setEmail(input.getEmail());
		optUser.get().setRole(input.getRole());
		userRepository.save(optUser.get());

		DtoUser dtoUser = new DtoUser();

		BeanUtils.copyProperties(optUser.get(), dtoUser);

		return dtoUser;
	}

	@Override
	public String deleteUser(Long id) {

		Optional<Users> optUser = userRepository.findById(id);

		if (optUser.isEmpty()) {
			throw new BaseException(new ErrorMessage(id.toString(), MessageType.NO_RECORD_EXIST));
		}

		userRepository.delete(optUser.get());

		return "USER DELETED";

	}

	@Override
	public String changePassword(Long id, String newPassword) {

		Optional<Users> optUser = userRepository.findById(id);
		if (optUser.isEmpty()) {
			throw new BaseException(new ErrorMessage(id.toString(), MessageType.NO_RECORD_EXIST));
		}

		optUser.get().setPassword(encoder.encode(newPassword));
		userRepository.save(optUser.get());

		return "Password changed: " + optUser.get().getPassword();
	}

}
