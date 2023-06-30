package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;

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
		if (order.getBook() == null) {
			throw new IntegrityViolation("Livro está vazio");
		}
	}

	private void validSale(Order order) {
		if (order.getSale() == null) {
			throw new IntegrityViolation("Venda está vazia");
		}
	}

	private void validQuantityBook(Order order) {
		if (order.getQuantityBook() == null) {
			throw new IntegrityViolation("Quantidade de livro está vazio");
		}
	}

	@Override
	public List<Order> findAll() {
		List<Order> list = repository.findAll();
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum pedido encontrado");
		}
		return list;
	}

	@Override
	public Order findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Id: %s do pedido não encontrado".formatted(id)));
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
	public List<Order> findByBook(Book book) {
		List<Order> order = repository.findByBook(book);
		if (order.isEmpty()) {
			throw new ObjectNotFound("Nenhum Livro encontrado no pedido");
		}
		return order;
	}

	@Override
	public List<Order> findBySale(Sale sale) {
		List<Order> order = repository.findBySale(sale);
		if (order.isEmpty()) {
			throw new ObjectNotFound("Nenhuma venda encontrada no pedido");
		}
		return order;
	}
}
