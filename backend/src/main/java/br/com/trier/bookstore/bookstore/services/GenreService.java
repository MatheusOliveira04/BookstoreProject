package br.com.trier.bookstore.bookstore.services;

import java.util.List;

import br.com.trier.bookstore.bookstore.models.Genre;

public interface GenreService {

	List<Genre> findAll();
	
	Genre findById(Integer id);
	
	Genre insert(Genre genre);

	Genre update(Genre genre);
	
	void delete(Integer id);
}
