package com.libraryMs.dto;

import com.libraryMs.enums.CategoryType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoBook {

	private Long id;

	private String title;

	private String author;

	private String isbn;

	private CategoryType bookCategory;

	private Long stock;

}
