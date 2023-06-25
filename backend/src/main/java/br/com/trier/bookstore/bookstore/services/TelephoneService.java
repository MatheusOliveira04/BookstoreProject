package br.com.trier.bookstore.bookstore.services;

import java.util.List;

import br.com.trier.bookstore.bookstore.models.Telephone;

public interface TelephoneService {

	List<Telephone> findAll();
	
	Telephone findById(Integer id);
	
	Telephone insert(Telephone telephone);
	
	Telephone update(Telephone telephone);
	
	void delete(Integer id);
}
