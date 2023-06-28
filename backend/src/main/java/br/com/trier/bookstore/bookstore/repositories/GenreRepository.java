package br.com.trier.bookstore.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>{

	Genre findByDescriptionIgnoreCase(String name);
}
