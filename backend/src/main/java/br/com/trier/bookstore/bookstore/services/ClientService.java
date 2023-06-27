package br.com.trier.bookstore.bookstore.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Telephone;

public interface ClientService {

	List<Client> findAll();
	
	Client findById(Integer id);
	
	Client insert(Client client);
	
	Client update(Client client);
	
	void delete(Integer id);
	
	Optional<Client> findByAddressOrderByName(Address address);
	
	Optional<Client> findByTelephoneOrderByName(Telephone telephone);
}
