package com.libraryMs.service;

import java.util.List;

import com.libraryMs.model.BorrowRecord;

public interface IBorrowRecordService {

	public BorrowRecord createBorrowBook(Long userId, Long bookId);

	public BorrowRecord returnBorrowBook(Long recordId);

	public BorrowRecord getUserBorrowRecord(Long borrowId);

	public List<BorrowRecord> getAllBorrowRecords();

}
