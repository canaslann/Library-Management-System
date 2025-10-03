package com.libraryMs.controller;

import java.util.List;

import com.libraryMs.model.BorrowRecord;

public interface IRestBorrowRecordController {

	public RootEntity<BorrowRecord> createBorrowBook(Long userId, Long bookId);

	public RootEntity<BorrowRecord> returnBorrowBook(Long recordId);

	public RootEntity<BorrowRecord> getUserBorrowRecord(Long borrowId);

	public RootEntity<List<BorrowRecord>> getAllBorrowRecords();

}
