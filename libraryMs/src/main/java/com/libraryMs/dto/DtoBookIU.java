package com.libraryMs.dto;

import com.libraryMs.enums.CategoryType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoBookIU {

	@NotNull
	private String title;

	@NotNull
	private String author;

	@NotNull
	private String isbn;

	@NotNull
	private CategoryType bookCategory;

	@NotNull
	private Long stock;

}
