package com.libraryMs.service;

import java.util.List;

import com.libraryMs.dto.DtoBook;
import com.libraryMs.dto.DtoBookIU;

public interface IBookService {

	public DtoBook saveBook(DtoBookIU input);

	public DtoBook updateBook(Long id, DtoBookIU input);

	public String deleteBook(Long id);

	public DtoBook getBook(Long id);

	public List<DtoBook> getAllBooks();

}
