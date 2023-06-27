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
import br.com.trier.bookstore.bookstore.services.AddressService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class AddressServiceTest extends BaseTest{
	
	@Autowired
	AddressService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	void findByIdTest() {
		Address address = service.findById(3);
		assertNotNull(address);
		assertEquals("Rua 1", address.getStreet());
		assertEquals("Bairro 1", address.getNeighborhood());
		assertEquals(3, address.getCity().getId());
	}

	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 do endereço não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	void findAllTest(){
		List<Address> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum endereço encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void insertTest() {
		Address address = new Address(1, "Rua", "Bairro", new City(3, "Gravatal", "SC"));
		address = service.insert(address);
		assertNotNull(address);
		assertEquals(1, address.getId());
		assertEquals("Rua", address.getStreet());
		assertEquals("Bairro", address.getNeighborhood());
	}
	
	@Test
	@DisplayName("Teste inserir com cidade vazia")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void insertCityEmptyTest() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.insert(new Address(1, "Rua", "Bairro", null)));
		assertEquals("No endereço, o campo cidade não pode ser vazio", exception.getMessage());
	}

	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	void updateTest() {
		Address address = new Address(3, "Rua", "Bairro", new City(3, "Gravatal", "SC"));
		address = service.update(address);
		assertNotNull(address);
		assertEquals(3, address.getId());
		assertEquals("Rua", address.getStreet());
		assertEquals("Bairro", address.getNeighborhood());
	}
	
	@Test
	@DisplayName("Teste atualizar com cidade vazia")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void updateCityEmptyTest() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.insert(new Address(1, "Rua", "Bairro", null)));
		assertEquals("No endereço, o campo cidade não pode ser vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	void deleteTest() {
		List<Address> list = service.findAll();
		assertEquals(2, list.size());
		service.delete(3);
		list = service.findAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar id não encontrado")
	void deleteIfNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.delete(10));
		assertEquals("Id: 10 do endereço não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por cidade")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	void findByCityTest() {
		List<Address> list = service.findByCity(new City(3, "", "aa"));
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por cidade nenhum encontrado")
	void findAddressByCityNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByCity(new City(3, "test", "")));
		assertEquals("A cidade: test do endereço não foi encontrada",
				exception.getMessage());
	}
}
