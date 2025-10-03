package com.libraryMs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.libraryMs.dto.DtoBook;
import com.libraryMs.dto.DtoBookIU;
import com.libraryMs.enums.MessageType;
import com.libraryMs.exception.BaseException;
import com.libraryMs.exception.ErrorMessage;
import com.libraryMs.model.Book;
import com.libraryMs.repository.BookRepository;
import com.libraryMs.service.IBookService;

@Service
public class BookService implements IBookService {

	@Autowired
	private BookRepository bookRepository;

	private Book createBook(DtoBookIU input) {
		Book book = new Book();
		BeanUtils.copyProperties(input, book);

		return book;
	}

	@Override
	public DtoBook saveBook(DtoBookIU input) {
		DtoBook dtoBook = new DtoBook();
		Book savedBook = bookRepository.save(createBook(input));

		BeanUtils.copyProperties(savedBook, dtoBook);

		return dtoBook;
	}

	@Override
	public DtoBook updateBook(Long id, DtoBookIU input) {
		Optional<Book> optional = bookRepository.findById(id);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(id.toString(), MessageType.NO_RECORD_EXIST));
		}

		optional.get().setAuthor(input.getAuthor());
		optional.get().setBookCategory(input.getBookCategory());
		optional.get().setIsbn(input.getIsbn());
		optional.get().setStock(input.getStock());
		optional.get().setTitle(input.getTitle());

		bookRepository.save(optional.get());
		DtoBook dtoBook = new DtoBook();
		BeanUtils.copyProperties(optional.get(), dtoBook);

		return dtoBook;
	}

	@Override
	public String deleteBook(Long id) {

		Optional<Book> optBook = bookRepository.findById(id);
		if (optBook.isEmpty()) {
			throw new BaseException(new ErrorMessage(id.toString(), MessageType.NO_RECORD_EXIST));
		}
		bookRepository.delete(optBook.get());

		return "Book deleted successfull";
	}

	@Override
	public DtoBook getBook(Long id) {
		Optional<Book> optBook = bookRepository.findById(id);
		if (optBook.isEmpty()) {
			throw new BaseException(new ErrorMessage(id.toString(), MessageType.NO_RECORD_EXIST));
		}
		DtoBook dtoBook = new DtoBook();
		BeanUtils.copyProperties(optBook.get(), dtoBook);

		return dtoBook;
	}

	@Override
	public List<DtoBook> getAllBooks() {
		List<Book> bookList = bookRepository.findAll();
		List<DtoBook> dtoBookList = new ArrayList<>();

		for (Book b : bookList) {
			DtoBook dto = new DtoBook();
			BeanUtils.copyProperties(b, dto);
			dtoBookList.add(dto);
		}
		return dtoBookList;
	}

}
