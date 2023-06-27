package br.com.trier.bookstore.bookstore.services;

import java.util.List;

import br.com.trier.bookstore.bookstore.models.Author;

public interface AuthorService {

	List<Author> findAll();
	
	Author findById(Integer id);
	
	Author insert(Author author);

	Author update(Author author);
	
	void delete(Integer id);
}
