package br.com.trier.bookstore.bookstore.services;

import java.util.List;

import br.com.trier.bookstore.bookstore.models.Book;

public interface BookService {

	List<BookService> findAll();
	
	Book findById(Integer id);
	
	Book insert(Book book);

	Book update(Book book);
	
	void delete(Integer id);
}
