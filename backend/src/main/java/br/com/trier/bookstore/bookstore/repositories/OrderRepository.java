package br.com.trier.bookstore.bookstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.Order;
import br.com.trier.bookstore.bookstore.models.Sale;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	List<Order> findByBook(Book book);
	List<Order> findBySale(Sale sale);
}
