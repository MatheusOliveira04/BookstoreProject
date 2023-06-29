package br.com.trier.bookstore.bookstore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

	Optional<Book> findByName(String name);
}
