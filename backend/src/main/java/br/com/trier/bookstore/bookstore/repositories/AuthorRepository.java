package br.com.trier.bookstore.bookstore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{

	Optional<Author> findByName(String name);
}
