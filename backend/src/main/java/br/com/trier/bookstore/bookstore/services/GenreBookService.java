package br.com.trier.bookstore.bookstore.services;

import java.util.List;

import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.Genre;
import br.com.trier.bookstore.bookstore.models.GenreBook;

public interface GenreBookService {

	List<GenreBook> findAll();
	
	GenreBook findById(Integer id);
	
	GenreBook insert(GenreBook genreBook);
	
	GenreBook update(GenreBook genreBook);
	
	void delete(Integer id);
	
	List<GenreBook> findByBook(Book book);
	
	List<GenreBook> findByGenre(Genre genre);
}
