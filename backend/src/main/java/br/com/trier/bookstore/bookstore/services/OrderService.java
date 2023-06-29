package br.com.trier.bookstore.bookstore.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.Order;
import br.com.trier.bookstore.bookstore.models.Sale;

public interface OrderService {

	List<Order> findAll();
	
	Order findById(Integer id);
	
	Order insert(Order order);

	Order update(Order order);
	
	void delete(Integer id);
	
	List<Order> findByBook(Book book);
	
	List<Order> findBySale(Sale sale);
}
