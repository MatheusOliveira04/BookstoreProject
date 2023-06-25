package br.com.trier.bookstore.bookstore.services;

import java.util.List;

import br.com.trier.bookstore.bookstore.models.City;

public interface CityService {

	List<City> findAll();
	
	City findById(Integer id);
	
	City insert(City city);
	
	City update(City city);
	
	void delete(Integer id);
	
	City findByNameIgnoreCase(String name);
}
