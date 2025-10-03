package com.libraryMs.controller;

import java.util.List;

import com.libraryMs.dto.DtoBook;
import com.libraryMs.dto.DtoBookIU;

public interface IRestBookController {

	public RootEntity<DtoBook> saveBook(DtoBookIU input);

	public RootEntity<DtoBook> updateBook(Long id, DtoBookIU input);

	public String deleteBook(Long id);

	public RootEntity<DtoBook> getBook(Long id);

	public List<DtoBook> getAllBooks();

}
