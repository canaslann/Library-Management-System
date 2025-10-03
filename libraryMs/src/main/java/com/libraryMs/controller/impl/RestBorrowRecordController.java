package com.libraryMs.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libraryMs.controller.IRestBorrowRecordController;
import com.libraryMs.controller.RestBaseController;
import com.libraryMs.controller.RootEntity;
import com.libraryMs.model.BorrowRecord;
import com.libraryMs.service.IBorrowRecordService;

@RestController
@RequestMapping("/rest/api/borrowRecord")
public class RestBorrowRecordController extends RestBaseController implements IRestBorrowRecordController {

	@Autowired
	IBorrowRecordService borrowRecordService;

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createBorrowBook/{userId}-{bookId}")
	public RootEntity<BorrowRecord> createBorrowBook(@PathVariable Long userId, @PathVariable Long bookId) {
		return ok(borrowRecordService.createBorrowBook(userId, bookId));
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/returnBorrowBook/{recordId}")
	public RootEntity<BorrowRecord> returnBorrowBook(@PathVariable Long recordId) {

		return ok(borrowRecordService.returnBorrowBook(recordId));
	}

	@GetMapping("/getUserBorrowRecord/{borrowId}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public RootEntity<BorrowRecord> getUserBorrowRecord(@PathVariable Long borrowId) {

		return ok(borrowRecordService.getUserBorrowRecord(borrowId));
	}

	@GetMapping("/getAllBorrowRecords")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public RootEntity<List<BorrowRecord>> getAllBorrowRecords() {

		return ok(borrowRecordService.getAllBorrowRecords());
	}

}
