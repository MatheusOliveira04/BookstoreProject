package br.com.trier.bookstore.bookstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.Genre;
import br.com.trier.bookstore.bookstore.models.GenreBook;

@Repository
public interface GenreBookRepository extends JpaRepository<GenreBook, Integer>{
	
	List<GenreBook> findByBook(Book book);
	List<GenreBook> findByGenre(Genre genre);

}
