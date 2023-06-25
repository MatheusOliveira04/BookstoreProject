package br.com.trier.bookstore.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

}
