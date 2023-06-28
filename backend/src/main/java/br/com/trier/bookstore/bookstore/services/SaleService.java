package br.com.trier.bookstore.bookstore.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Sale;
import br.com.trier.bookstore.bookstore.models.Salesperson;

public interface SaleService {

	List<Sale> findAll();
	
	Sale findById(Integer id);
	
	Sale insert(Sale sale);

	Sale update(Sale sale);
	
	void delete(Integer id);
	
	List<Sale> findBySalesperson(Salesperson salesperson);
	
	List<Sale> findByClient(Client client);
}
