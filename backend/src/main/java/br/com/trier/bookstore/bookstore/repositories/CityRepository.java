package br.com.trier.bookstore.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{

	City findByNameIgnoreCase(String name);
}
