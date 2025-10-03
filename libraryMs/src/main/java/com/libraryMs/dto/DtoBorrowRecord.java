package com.libraryMs.dto;

import java.util.Date;
import com.libraryMs.enums.BookStatusType;
import com.libraryMs.model.Book;
import com.libraryMs.model.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoBorrowRecord {

	private Long id;

	private Users users;

	private Book book;

	private Date borrowDate;

	private Date returnDate;

	private BookStatusType status;
}
