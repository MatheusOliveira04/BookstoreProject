package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.Order;
import br.com.trier.bookstore.bookstore.models.Sale;
import br.com.trier.bookstore.bookstore.repositories.OrderRepository;
import br.com.trier.bookstore.bookstore.services.OrderService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository repository;

	private void validBook(Order order) {
		if(order.getBook() == null) {
			throw new IntegrityViolation("Livro está vazio");
		}
	}
	private void validSale(Order order) {
		if(order.getSale() == null) {
			throw new IntegrityViolation("Venda está vazio");
		}
	}
	
	private void validQuantityBook(Order order) {
		if(order.getQuantityBook() == null) {
			throw new IntegrityViolation("Quantidade está vazio");
		}
	}
	
	@Override
	public List<Order> listAll() {
		List<Order> list = repository.findAll();
		if(list.isEmpty()){
			throw new ObjectNotFound("Nenhum perdido encontrado");
		}
		return list;
	}

	@Override
	public Order findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s do pedido não encontrado"));
	}

	@Override
	public Order insert(Order order) {
		validBook(order);
		validSale(order);
		validQuantityBook(order);
		return repository.save(order);
	}

	@Override
	public Order update(Order order) {
		findById(order.getId());
		validBook(order);
		validSale(order);
		validQuantityBook(order);
		return repository.save(order);
	}

	@Override
	public void delete(Integer id) {
		Order order = findById(id);
		repository.delete(order);
		
	}

	@Override
	public Optional<Order> findByBook(Book book) {
		Optional<Order> order = repository.findByBook(book);
		if(order.isEmpty()){
			throw new ObjectNotFound("Nenhum Livro encontrado");
		}
		return order;
	}

	@Override
	public Optional<Order> findBySale(Sale sale) {
		Optional<Order> order = repository.findBySale(sale);
		if(order.isEmpty()){
			throw new ObjectNotFound("Nenhuma venda encontrada");
		}
		return order;
	}
}
