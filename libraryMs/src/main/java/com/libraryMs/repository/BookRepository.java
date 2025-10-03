package com.libraryMs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libraryMs.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
