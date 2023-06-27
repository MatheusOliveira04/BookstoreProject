package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Sale;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.repositories.SaleRepository;
import br.com.trier.bookstore.bookstore.services.SaleService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class SaleServiceImpl implements SaleService{

	@Autowired
	SaleRepository repository;

	private void validClient(Sale sale) {
		if(sale.getClient() == null) {
			throw new IntegrityViolation("Cliente está vazio");
		}
	}
	
	@Override
	public List<Sale> findAll() {
		List<Sale> list = repository.findAll();
		if(list.isEmpty()){
			throw new ObjectNotFound("Nenhuma venda encontrada");
		}
		return list;
	}

	@Override
	public Sale findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s da venda não encontrado"));
	}

	@Override
	public Sale insert(Sale sale) {
		validClient(sale);
		return repository.save(sale);
	}

	@Override
	public Sale update(Sale sale) {
		findById(sale.getId());
		validClient(sale);
		return repository.save(sale);
	}

	@Override
	public void delete(Integer id) {
		Sale sale = findById(id);
		repository.delete(sale);
	}

	@Override
	public Optional<Sale> findBySalesperson(Salesperson salesperson) {
		Optional<Sale> sale = repository.findBySalesperson(salesperson);
		if(sale.isEmpty()){
			throw new ObjectNotFound("Nenhum vendedor encontrado");
		}
		return sale;
	}

	@Override
	public Optional<Sale> findByClient(Client client) {
		Optional<Sale> sale = repository.findByClient(client);
		if(sale.isEmpty()){
			throw new ObjectNotFound("Nenhum cliente encontrado");
		}
		return sale;
	}
}
