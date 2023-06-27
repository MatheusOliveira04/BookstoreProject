package br.com.trier.bookstore.bookstore.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.City;

public interface AddressService {

	List<Address> findAll();
	
	Address findById(Integer id);
	
	Address insert(Address address);
	
	Address update(Address address);
	
	void delete(Integer id);
	
	List<Address> findByCity(City city);
}
