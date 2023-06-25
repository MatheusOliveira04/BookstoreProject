package br.com.trier.bookstore.bookstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Author;
import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.BookAuthor;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Integer>{

	List<BookAuthor> findByAuthor(Author author);
	List<BookAuthor> findByBook(Book book);
}
