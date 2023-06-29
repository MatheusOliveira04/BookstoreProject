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
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
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
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void insert() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Salesperson salerperson = new Salesperson(1, "insert","100.100.100-11", address, telephone);
		salerperson = service.insert(salerperson);
		assertNotNull(salerperson);
		assertEquals(1, salerperson.getId());
		assertEquals("insert", salerperson.getName());
		assertEquals("100.100.100-11", salerperson.getCpf());
		assertEquals(3, salerperson.getTelephone().getId());
		assertEquals(3, salerperson.getAddress().getId());
	}
	
	@Test
	@DisplayName("Teste inserir com nome vazio")
	void insertNameEmptyTest() {
	Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
	Telephone telephone = new Telephone(3, "(00)00000-0000");
	Salesperson salerperson = new Salesperson(3, "  " , "000.000.000-00", address, telephone);
	var exception = assertThrows(IntegrityViolation.class, 
			() -> service.insert(salerperson));
	assertEquals("Nome do vendedor está vazio", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir com cpf vazio")
	void insertCpfIsNullTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Salesperson salerperson = new Salesperson(3, "insert" ,null, address, telephone);
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.insert(salerperson));
		assertEquals("Cpf do vendedor está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir com formato cpf inválido")
	void insertCpfFormatInvalidTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Salesperson salerperson = new Salesperson(3, "insert" ,"00111000---0", address, telephone);
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.insert(salerperson));
		assertEquals("Formato do cpf inválido, favor utilizar o formato: 000.000.000-00", 
				exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir com cpf já existente")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void insertCpfDuplicateTest() {
			Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
			Telephone telephone = new Telephone(3, "(00)00000-0000");
			Salesperson salerperson = new Salesperson(5, "insert" ,"100.100.100-11", address, telephone);
			var exception = assertThrows(IntegrityViolation.class, 
					() -> service.insert(salerperson));
			assertEquals("O vendedor já contém o cpf 100.100.100-11", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void update() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Salesperson salerperson = new Salesperson(3, "update","000.000.000-00", address, telephone);
		salerperson = service.update(salerperson);
		assertNotNull(salerperson);
		assertEquals(3, salerperson.getId());
		assertEquals("update", salerperson.getName());
		assertEquals("000.000.000-00", salerperson.getCpf());
	}
	
	@Test
	@DisplayName("Teste atualizar com id não existe")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void updateIdNotFoundTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Salesperson salesperson = new Salesperson(10, "update","000.000.000-00", address, telephone);
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.update(salesperson));
		assertEquals("Id: 10 do vendedor não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar com nome vazio")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void updateNameEmptyTest() {
	Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
	Telephone telephone = new Telephone(3, "(00)00000-0000");
	Salesperson salerperson = new Salesperson(3, null, "000.000.000-00", address, telephone);
	var exception = assertThrows(IntegrityViolation.class, 
			() -> service.update(salerperson));
	assertEquals("Nome do vendedor está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza com cpf vazio")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void updateCpfIsNullTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Salesperson salerperson = new Salesperson(3, "update" ,null, address, telephone);
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.update(salerperson));
		assertEquals("Cpf do vendedor está vazio", exception.getMessage());
	}

	@Test
	@DisplayName("Teste atualiza com formato do cpf vazio")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void updateCpfFormatInvalidTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Salesperson salerperson = new Salesperson(3, "update" ,"1100011--0", address, telephone);
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.update(salerperson));
		assertEquals("Formato do cpf inválido, favor utilizar o formato: 000.000.000-00", 
				exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar com cpf já existente")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void updateCpfDuplicateTest() {
			Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
			Telephone telephone = new Telephone(3, "(00)00000-0000");
			Salesperson salerperson = new Salesperson(4, "update" ,"100.100.100-11", address, telephone);
			var exception = assertThrows(IntegrityViolation.class, 
					() -> service.update(salerperson));
			assertEquals("O vendedor já contém o cpf 100.100.100-11", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void deleteTest() {
		List<Salesperson> list = service.findAll();
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
		assertEquals("Id: 10 do vendedor não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por cpf")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void findByCpfTest() {
		Salesperson salesperson = service.findByCpf("100.100.100-11");
		assertEquals(3, salesperson.getId());
		assertEquals("Alex", salesperson.getName());
		assertEquals(3, salesperson.getAddress().getId());
		assertEquals(3, salesperson.getTelephone().getId());
	}
	
	@Test
	@DisplayName("Teste buscar por cpf inexistente")
	void findByCpfNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByCpf("999.888.100-11"));
		assertEquals("cpf: 999.888.100-11 não encontrado no vendedor", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por endereço")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void findByAddressTest() {
		Address address = new Address(3, null, null, null);
		List<Salesperson> list = service.findByAddressOrderByName(address);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por endereço nenhum encontrado")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void findByAddresNotFoundTest() {
		Address address = new Address(10, null, null, null);
		var exception = assertThrows(ObjectNotFound.class,
				() -> service.findByAddressOrderByName(address));
		assertEquals("O endereço: 10 do vendedor não foi encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por telefone")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void findByTelephoneTest() {
		Telephone telephone = new Telephone(3, null);
		List<Salesperson> list = service.findByTelephoneOrderByName(telephone);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por telefone nenhum encontrado")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/salesperson.sql"})
	void findByTelephoneNotFoundTest() {
		Telephone telephone = new Telephone(10, null);
		var exception = assertThrows(ObjectNotFound.class,
				() -> service.findByTelephoneOrderByName(telephone));
		assertEquals("O Telefone: 10 do vendedor não foi encontrado", exception.getMessage());
	}
}
