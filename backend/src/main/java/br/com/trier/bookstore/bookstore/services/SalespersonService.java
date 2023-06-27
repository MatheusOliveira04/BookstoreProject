package br.com.trier.bookstore.bookstore.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.Telephone;

public interface SalespersonService {

	List<Salesperson> findAll();
	
	Salesperson findById(Integer id);
	
	Salesperson insert(Salesperson salesperson);

	Salesperson update(Salesperson salesperson);
	
	void delete(Integer id);
	
	Optional<Salesperson> findByAddressOrderByName(Address address);
	
	Optional<Salesperson> findByTelephoneOrderByName(Telephone telephone);
}
