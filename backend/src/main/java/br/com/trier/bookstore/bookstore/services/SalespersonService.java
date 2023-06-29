package br.com.trier.bookstore.bookstore.services;

import java.util.List;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.Telephone;

public interface SalespersonService {

	List<Salesperson> findAll();
	
	Salesperson findById(Integer id);
	
	Salesperson insert(Salesperson salesperson);

	Salesperson update(Salesperson salesperson);
	
	void delete(Integer id);
	
	Salesperson findByCpf(String cpf);
	
	List<Salesperson> findByAddressOrderByName(Address address);
	
	List<Salesperson> findByTelephoneOrderByName(Telephone telephone);
}
