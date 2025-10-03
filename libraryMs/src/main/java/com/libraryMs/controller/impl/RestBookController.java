package com.libraryMs.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.libraryMs.controller.IRestBookController;
import com.libraryMs.controller.RestBaseController;
import com.libraryMs.controller.RootEntity;
import com.libraryMs.dto.DtoBook;
import com.libraryMs.dto.DtoBookIU;
import com.libraryMs.service.IBookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/book")
public class RestBookController extends RestBaseController implements IRestBookController {

	@Autowired
	private IBookService bookService;

	@PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public RootEntity<DtoBook> saveBook(@RequestBody @Valid DtoBookIU input) {

		return ok(bookService.saveBook(input));
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public RootEntity<DtoBook> updateBook(@PathVariable Long id, @Valid @RequestBody DtoBookIU input) {
		return ok(bookService.updateBook(id, input));
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public String deleteBook(@PathVariable Long id) {

		return bookService.deleteBook(id);
	}

	@GetMapping("/findById/{id}")
	@PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
	@Override
	public RootEntity<DtoBook> getBook(@PathVariable Long id) {

		return ok(bookService.getBook(id));
	}

	@GetMapping("/getAllBooks")
	@PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
	@Override
	public List<DtoBook> getAllBooks() {
		return bookService.getAllBooks();
	}

}
