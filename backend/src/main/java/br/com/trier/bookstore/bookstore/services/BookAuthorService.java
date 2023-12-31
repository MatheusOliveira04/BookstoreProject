package br.com.trier.bookstore.bookstore.services;

import java.util.List;

import br.com.trier.bookstore.bookstore.models.Author;
import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.BookAuthor;

public interface BookAuthorService {

	List<BookAuthor> findAll();
	
	BookAuthor findById(Integer id);
	
	BookAuthor insert(BookAuthor bookAuthor);

	BookAuthor update(BookAuthor bookAuthor);
	
	void delete(Integer id);
	
	List<BookAuthor> findByAuthor(Author author);
	
	List<BookAuthor> findByBook(Book book);
}
