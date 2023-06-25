package br.com.trier.bookstore.bookstore.services;

import java.util.List;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.Telephone;

public interface ClientService {

	List<ClientService> findAll();
	
	ClientService findById(Integer id);
	
	ClientService insert(ClientService client);
	
	ClientService update(ClientService client);
	
	void delete(Integer id);
	
	List<ClientService> findByAddressOrderByName(Address address);
	
	List<ClientService> findByTelephoneOrderByName(Telephone telephone);
}
