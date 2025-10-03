package com.libraryMs.dto;

import com.libraryMs.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser {

	private Long id;

	private String username;

	private String email;

	private String password;

	private RoleType role;

}
