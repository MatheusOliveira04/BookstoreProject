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
import br.com.trier.bookstore.bookstore.models.City;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.Telephone;
import br.com.trier.bookstore.bookstore.services.SalespersonService;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class SalespersonServiceTest extends BaseTest {
	
	@Autowired
	SalespersonService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void findByIdTest() {
		Salesperson salesperson = service.findById(3);
		assertNotNull(salesperson);
		assertEquals("100.100.100-11", salesperson.getCpf());
	}

	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 do vendedor não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void findAllTest(){
		List<Salesperson> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum vendedor encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	void insert() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Salesperson salersperson = new Salesperson(3, "insert", "000.000.000-00", address, telephone);
	}
	

}
