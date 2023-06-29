package br.com.trier.bookstore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.bookstore.BaseTest;
import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.City;
import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Order;
import br.com.trier.bookstore.bookstore.models.Sale;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.Telephone;
import br.com.trier.bookstore.bookstore.services.OrderService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class OrderServiceTest extends BaseTest {

	@Autowired
	OrderService service;

	@Test
	@DisplayName("Teste buscar por id")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	@Sql("classpath:/resources/sqls/order.sql")
	void findByIdTest() {
		Order order = service.findById(3);
		assertEquals(3, order.getBook().getId());
		assertEquals(3, order.getSale().getId());
		assertEquals(10, order.getQuantityBook());
	}

	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 do pedido não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar todos")
	@Sql({ "classpath:/resources/sqls/city.sql" })
	@Sql({ "classpath:/resources/sqls/address.sql" })
	@Sql({ "classpath:/resources/sqls/telephone.sql" })
	@Sql({ "classpath:/resources/sqls/salesperson.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	@Sql("classpath:/resources/sqls/order.sql")
	void findAllTest() {
		List<Order> list = service.findAll();
		assertEquals(2, list.size());
	}

	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum pedido encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir")
	@Sql({ "classpath:/resources/sqls/city.sql" })
	@Sql({ "classpath:/resources/sqls/address.sql" })
	@Sql({ "classpath:/resources/sqls/telephone.sql" })
	@Sql({ "classpath:/resources/sqls/salesperson.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	void insertTest() {
		City city = new City(3, null, null);
		Address address = new Address(3, null, null, city);
		Telephone telephone = new Telephone(3, null);
		Client client = new Client(3, null, null, address, telephone);
		Salesperson salesperson = new Salesperson(3, null, null, address, telephone);
		Sale sale = new Sale(3, client, salesperson);
		Book book = new Book(3, "Mar");
		Order order = new Order(1, 10, book, sale);
		order = service.insert(order);
		assertNotNull(order);
		assertEquals(1, order.getId());
		assertEquals(3, order.getSale().getId());
		assertEquals(3, order.getBook().getId());
	}

	@Test
	@DisplayName("Teste inserir com livro vazio")
	void insertBookIsEmptyTest() {
		City city = new City(3, null, null);
		Address address = new Address(3, null, null, city);
		Telephone telephone = new Telephone(3, null);
		Client client = new Client(3, null, null, address, telephone);
		Salesperson salesperson = new Salesperson(3, null, null, address, telephone);
		Sale sale = new Sale(1, client, salesperson);
		Order order = new Order(1, 10, null, sale);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(order));
		assertEquals("Livro está vazio", exception.getMessage());

	}
	
	@Test
	@DisplayName("Teste inserir com venda vazio")
	void insertSaleIsEmptyTest() {
		Book book = new Book(1, "Mar");
		Order order = new Order(1, 10, book, null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(order));
		assertEquals("Venda está vazia", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste inserir com quantidade de livro vazio")
	void insertQuantityBookIsEmptyTest() {
		City city = new City(3, null, null);
		Address address = new Address(3, null, null, city);
		Telephone telephone = new Telephone(3, null);
		Client client = new Client(3, null, null, address, telephone);
		Salesperson salesperson = new Salesperson(3, null, null, address, telephone);
		Sale sale = new Sale(1, client, salesperson);
		Book book = new Book(1, "Mar");
		Order order = new Order(1, null, book, sale);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(order));
		assertEquals("Quantidade de livro está vazio", exception.getMessage());
	}

	@Test
	@DisplayName("Teste atualizar")
	@Sql({ "classpath:/resources/sqls/city.sql" })
	@Sql({ "classpath:/resources/sqls/address.sql" })
	@Sql({ "classpath:/resources/sqls/telephone.sql" })
	@Sql({ "classpath:/resources/sqls/salesperson.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	@Sql("classpath:/resources/sqls/order.sql")
	void updateTest() {
		Sale sale = new Sale(3, null, null);
		Book book = new Book(3, null);
		Order order = new Order(4, 100, book, sale);
		order = service.update(order);
		assertNotNull(order);
		assertEquals(4, order.getId());
		assertEquals(3, order.getSale().getId());
		assertEquals(3, order.getBook().getId());
		assertEquals(100, order.getQuantityBook());
	}
	
	@Test
	@DisplayName("Teste atualizar com livro vazio")
	@Sql({ "classpath:/resources/sqls/city.sql" })
	@Sql({ "classpath:/resources/sqls/address.sql" })
	@Sql({ "classpath:/resources/sqls/telephone.sql" })
	@Sql({ "classpath:/resources/sqls/salesperson.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	@Sql("classpath:/resources/sqls/order.sql")
	void updateBookIsEmptyTest() {
		Sale sale = new Sale(4, null, null);
		Order order = new Order(3, 100, null, sale);
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(order));
		assertEquals("Livro está vazio", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste atualizar com venda vazio")
	@Sql({ "classpath:/resources/sqls/city.sql" })
	@Sql({ "classpath:/resources/sqls/address.sql" })
	@Sql({ "classpath:/resources/sqls/telephone.sql" })
	@Sql({ "classpath:/resources/sqls/salesperson.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	@Sql("classpath:/resources/sqls/order.sql")
	void updateSaleIsEmptyTest() {
		Book book = new Book(4, null);
		Order order = new Order(3, 100, book, null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(order));
		assertEquals("Venda está vazia", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste atualizar com quantidade de livro vazio")
	@Sql({ "classpath:/resources/sqls/city.sql" })
	@Sql({ "classpath:/resources/sqls/address.sql" })
	@Sql({ "classpath:/resources/sqls/telephone.sql" })
	@Sql({ "classpath:/resources/sqls/salesperson.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	@Sql("classpath:/resources/sqls/order.sql")
	void updateQuantityBookIsEmptyTest() {
		Sale sale = new Sale(4, null, null);
		Book book = new Book(4, null);
		Order order = new Order(3, null, book, sale);
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(order));
		assertEquals("Quantidade de livro está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({ "classpath:/resources/sqls/city.sql" })
	@Sql({ "classpath:/resources/sqls/address.sql" })
	@Sql({ "classpath:/resources/sqls/telephone.sql" })
	@Sql({ "classpath:/resources/sqls/salesperson.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	@Sql("classpath:/resources/sqls/order.sql")
	void deleteTest() {
		List<Order> list = service.findAll();
		assertEquals(2, list.size());
		service.delete(3);
		list = service.findAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar id não encontrado")
	void deleteIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.delete(10));
		assertEquals("Id: 10 do pedido não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por livro")
	@Sql({ "classpath:/resources/sqls/city.sql" })
	@Sql({ "classpath:/resources/sqls/address.sql" })
	@Sql({ "classpath:/resources/sqls/telephone.sql" })
	@Sql({ "classpath:/resources/sqls/salesperson.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	@Sql("classpath:/resources/sqls/order.sql")
	void findByBookTest() {
		Book book = new Book(3, null);
		List<Order> list = service.findByBook(book);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos por livro sem nenhum cadastro")
	void findBookAllEmptyTest() {
		Book book = new Book(10, null);
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByBook(book));
		assertEquals("Nenhum Livro encontrado no pedido", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por venda")
	@Sql({ "classpath:/resources/sqls/city.sql" })
	@Sql({ "classpath:/resources/sqls/address.sql" })
	@Sql({ "classpath:/resources/sqls/telephone.sql" })
	@Sql({ "classpath:/resources/sqls/salesperson.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	@Sql("classpath:/resources/sqls/order.sql")
	void findBySaleTest() {
		Sale sale = new Sale(3, null, null);
		List<Order> list = service.findBySale(sale);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos por venda sem nenhum cadastro")
	void findSaleAllEmptyTest() {
		Sale sale = new Sale(10, null, null);
		var exception = assertThrows(ObjectNotFound.class, () -> service.findBySale(sale));
		assertEquals("Nenhuma venda encontrada no pedido", exception.getMessage());
	}
}
