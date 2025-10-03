package com.libraryMs.enums;

import lombok.Getter;

@Getter
public enum MessageType {
	NO_RECORD_EXIST("1004", "Record is not found"), GENERAL_EXCEPTION("9999", "General exception"),
	TOKEN_IS_EXPIRED("1005", "Token is expired"), USERNAME_OR_PASSWORD_INVALID("1007", "Username or password invalid"),
	REFRESH_TOKEN_IS_EXPIRED("1008", "Refresh token was expire"), USERNAME_NOT_FOUND("1006", "Username not found"),
	BOOK_IS_ALREADY_BORROWED("1009", "Book is already borrowed"),
	BOOK_IS_ALREADY_RETURNED("1010", "Book is already returned");

	private String code;

	private String message;

	MessageType(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
