package br.com.trier.bookstore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.bookstore.BaseTest;
import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.BookAuthor;
import br.com.trier.bookstore.bookstore.models.City;
import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Genre;
import br.com.trier.bookstore.bookstore.models.Sale;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.Telephone;
import br.com.trier.bookstore.bookstore.services.SaleService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class SaleServiceTest extends BaseTest{

	@Autowired
	SaleService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	void findByIdTest() {
		Sale sale = service.findById(3);
		assertNotNull(sale);
		assertEquals(3, sale.getClient().getId());
		assertEquals("Alex", sale.getClient().getName());
		assertEquals("100.100.100-11", sale.getClient().getCpf());
		assertEquals(3, sale.getSalesperson().getId());
		assertEquals("Alex", sale.getSalesperson().getName());
		assertEquals("100.100.100-11", sale.getSalesperson().getCpf());
	}
	
	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 da venda não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	void findAllTest(){
		List<Sale> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhuma venda encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	void insertTest() {
		City city = new City(3,null, null);
		Address address = new Address(3,null, null, city);
		Telephone telephone = new Telephone(3, null);
		Client client = new Client(3, null, null, address, telephone);
		Salesperson salesperson = new Salesperson(3, null, null, address, telephone);
		Sale sale = new Sale(1, client, salesperson);
		sale = service.insert(sale);
		assertNotNull(sale);
		assertEquals(3, sale.getClient().getId());
		assertEquals("Alex", sale.getClient().getName());
		assertEquals("100.100.100-11", sale.getClient().getCpf());
		assertEquals(3, sale.getSalesperson().getId());
		assertEquals("Alex", sale.getSalesperson().getName());
		assertEquals("100.100.100-11", sale.getSalesperson().getCpf());
	}
	
	@Test
	@DisplayName("Teste inserir com cliente não cadastrado")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	void insertClientNullTest() {
		City city = new City(3,null, null);
		Address address = new Address(3,null, null, city);
		Telephone telephone = new Telephone(3, null);
		Salesperson salesperson = new Salesperson(3, null, null, address, telephone);
		Sale sale = new Sale(1, null, salesperson);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(sale));
		assertEquals("Cliente está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	void updateTest() {
		City city = new City(3,null, null);
		Address address = new Address(3,null, null, city);
		Telephone telephone = new Telephone(3, null);
		Client client = new Client(4, null, null, address, telephone);
		Salesperson salesperson = new Salesperson(4, null, null, address, telephone);
		Sale sale = new Sale(3, client, salesperson);
		sale = service.update(sale);
		assertNotNull(sale);
		assertEquals(4, sale.getClient().getId());
		assertEquals("Lucio", sale.getClient().getName());
		assertEquals("100.100.111-11", sale.getClient().getCpf());
		assertEquals(4, sale.getSalesperson().getId());
		assertEquals("Leo", sale.getSalesperson().getName());
		assertEquals("100.100.111-11", sale.getSalesperson().getCpf());
	}
	
	@Test
	@DisplayName("Teste atualizar com id não cadastrado")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	void updateIdNotFoundTest() {
		City city = new City(3,null, null);
		Address address = new Address(3,null, null, city);
		Telephone telephone = new Telephone(3, null);
		Salesperson salesperson = new Salesperson(3, null, null, address, telephone);
		Sale sale = new Sale(10, null, salesperson);
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(sale));
		assertEquals("Id: 10 da venda não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar com cliente não cadastrado")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	void updateClientNullTest() {
		City city = new City(3,null, null);
		Address address = new Address(3,null, null, city);
		Telephone telephone = new Telephone(3, null);
		Client client = new Client(4, null, null, address, telephone);
		Salesperson salesperson = new Salesperson(4, null, null, address, telephone);
		Sale sale = new Sale(3, null, salesperson);
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(sale));
		assertEquals("Cliente está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	void deleteTest() {
		List<Sale> list = service.findAll();
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
		assertEquals("Id: 10 da venda não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por cliente")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	void findClientAllTest(){
		City city = new City(3,null, null);
		Address address = new Address(3,null, null, city);
		Telephone telephone = new Telephone(3, null);
		Client client = new Client(3, null, null, address, telephone);
		List<Sale> list = service.findByClient(client);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por cliente sem nenhum cadastro")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	void findClientAllEmptyTest() {
		City city = new City(3,null, null);
		Address address = new Address(3,null, null, city);
		Telephone telephone = new Telephone(3, null);
		Client client = new Client(4, null, null, address, telephone);
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByClient(client));
		assertEquals("Nenhum cliente encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar por vendedor")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	void findSaleAllTest(){
		City city = new City(3,null, null);
		Address address = new Address(3,null, null, city);
		Telephone telephone = new Telephone(3, null);
		Salesperson salesperson = new Salesperson(3, null, null, address, telephone);
		List<Sale> list = service.findBySalesperson(salesperson);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por vendedor sem nenhum cadastro")
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	void findSaleAllEmptyTest() {
		City city = new City(3,null, null);
		Address address = new Address(3,null, null, city);
		Telephone telephone = new Telephone(3, null);
		Salesperson salesperson = new Salesperson(10, null, null, address, telephone);
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findBySalesperson(salesperson));
		assertEquals("Nenhum vendedor encontrado", exception.getMessage());
	}
}
