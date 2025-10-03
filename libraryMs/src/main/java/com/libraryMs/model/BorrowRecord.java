package com.libraryMs.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.libraryMs.enums.BookStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "borrowRecord")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Users users;

	@ManyToOne
	private Book book;

	@Column
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date borrowDate;

	@Column
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date returnDate;

	@Column
	@Enumerated(EnumType.STRING)
	private BookStatusType status;

}
