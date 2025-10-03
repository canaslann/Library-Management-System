package com.libraryMs.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.libraryMs.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libraryMs.enums.BookStatusType;
import com.libraryMs.enums.MessageType;
import com.libraryMs.exception.BaseException;
import com.libraryMs.exception.ErrorMessage;
import com.libraryMs.model.Book;
import com.libraryMs.model.BorrowRecord;
import com.libraryMs.repository.BookRepository;
import com.libraryMs.repository.BorrowRecordRepository;
import com.libraryMs.repository.UserRepository;
import com.libraryMs.service.IBorrowRecordService;

@Service
public class BorrowRecordService implements IBorrowRecordService {

	@Autowired
	BorrowRecordRepository borrowRecordRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BookRepository bookRepository;

	@Override
	public BorrowRecord createBorrowBook(Long userId, Long bookId) {
		Optional<Users> optUser = userRepository.findById(userId);
		if (optUser.isEmpty()) {
			throw new BaseException(new ErrorMessage(userId.toString(), MessageType.NO_RECORD_EXIST));
		}

		Optional<Book> optBook = bookRepository.findById(bookId);
		if (optBook.isEmpty()) {
			throw new BaseException(new ErrorMessage(bookId.toString(), MessageType.NO_RECORD_EXIST));
		}

		if (optBook.get().getStock() == 0) {
			throw new BaseException(
					new ErrorMessage(optBook.get().getStock().toString(), MessageType.BOOK_IS_ALREADY_BORROWED));
		}
		BorrowRecord borrowRecord = new BorrowRecord();
		borrowRecord.setBook(optBook.get());
		borrowRecord.setUsers(optUser.get());
		borrowRecord.setBorrowDate(new Date());
		borrowRecord.setReturnDate(null);
		borrowRecord.setStatus(BookStatusType.BORROWED);
		optBook.get().setStock(optBook.get().getStock() - 1);

		return borrowRecordRepository.save(borrowRecord);
	}

	@Override
	public BorrowRecord returnBorrowBook(Long recordId) {
		Optional<BorrowRecord> optBorrowRecord = borrowRecordRepository.findById(recordId);
		if (optBorrowRecord.isEmpty()) {
			throw new BaseException(new ErrorMessage(recordId.toString(), MessageType.NO_RECORD_EXIST));
		}

		if (optBorrowRecord.get().getStatus() == BookStatusType.RETURNED) {
			throw new BaseException(new ErrorMessage(optBorrowRecord.get().getStatus().toString(),
					MessageType.BOOK_IS_ALREADY_RETURNED));
		}

		optBorrowRecord.get().setStatus(BookStatusType.RETURNED);
		optBorrowRecord.get().setReturnDate(new Date());
		optBorrowRecord.get().getBook().setStock(optBorrowRecord.get().getBook().getStock() + 1);

		return borrowRecordRepository.save(optBorrowRecord.get());
	}

	@Override
	public BorrowRecord getUserBorrowRecord(Long borrowId) {
		Optional<BorrowRecord> optBorrow = borrowRecordRepository.findById(borrowId);
		if (optBorrow.isEmpty()) {
			throw new BaseException(new ErrorMessage(borrowId.toString(), MessageType.NO_RECORD_EXIST));
		}

		return optBorrow.get();
	}

	@Override
	public List<BorrowRecord> getAllBorrowRecords() {
		List<BorrowRecord> borrowRecords = borrowRecordRepository.findAll();

		return borrowRecords;
	}

}
