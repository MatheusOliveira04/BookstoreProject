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
import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.Telephone;
import br.com.trier.bookstore.bookstore.services.ClientService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class ClientServiceTest extends BaseTest{

	@Autowired
	ClientService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByIdTest() {
		Client client = service.findById(3);
		assertNotNull(client);
		assertEquals("100.100.100-11", client.getCpf());
	}

	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 do cliente não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findAllTest(){
		List<Client> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum cliente encontrado", exception.getMessage());
	}
	

	@Test
	@DisplayName("Teste inserir")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void insertTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Client client = new Client(2, "insert","100.100.100-11", address, telephone);
		client = service.insert(client);
		assertNotNull(client);
		assertEquals(1, client.getId());
		assertEquals("insert", client.getName());
		assertEquals("100.100.100-11", client.getCpf());
		assertEquals(3, client.getTelephone().getId());
		assertEquals(3, client.getAddress().getId());
	}
	
	@Test
	@DisplayName("Teste inserir com nome vazio")
	void insertNameEmptyTest() {
	Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
	Telephone telephone = new Telephone(3, "(00)00000-0000");
	Client client = new Client(3, "  " , "000.000.000-00", address, telephone);
	var exception = assertThrows(IntegrityViolation.class, 
			() -> service.insert(client));
	assertEquals("Nome do cliente está vazio", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir com cpf vazio")
	void insertCpfIsNullTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Client client = new Client(3, "insert" ,null, address, telephone);
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.insert(client));
		assertEquals("Cpf do cliente está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir com formato cpf inválido")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void insertCpfFormatInvalidTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Client client = new Client(1, "insert" ,"00141100010001100", address, telephone);
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.insert(client));
		assertEquals("Formato do cpf inválido, favor utilizar o formato: 000.000.000-00", 
				exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir com cpf já existente")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void insertCpfDuplicateTest() {
			Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
			Telephone telephone = new Telephone(3, "(00)00000-0000");
			Client client = new Client(5, "insert" ,"100.100.100-11", address, telephone);
			var exception = assertThrows(IntegrityViolation.class, 
					() -> service.insert(client));
			assertEquals("O cliente já contém o cpf 100.100.100-11", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void update() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Client client = new Client(3, "update","000.000.000-00", address, telephone);
		client = service.update(client);
		assertNotNull(client);
		assertEquals(3, client.getId());
		assertEquals("update", client.getName());
		assertEquals("000.000.000-00", client.getCpf());
	}
	
	@Test
	@DisplayName("Teste atualizar com id não existe")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void updateIdNotFoundTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Client client = new Client(10, "update","000.000.000-00", address, telephone);
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.update(client));
		assertEquals("Id: 10 do cliente não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar com nome vazio")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void updateNameEmptyTest() {
	Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
	Telephone telephone = new Telephone(3, "(00)00000-0000");
	Client client = new Client(3, null, "000.000.000-00", address, telephone);
	var exception = assertThrows(IntegrityViolation.class, 
			() -> service.update(client));
	assertEquals("Nome do cliente está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza com formato do cpf vazio")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void updateCpfFormatInvalidTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Client client = new Client(3, "update" ,"1100011--0", address, telephone);
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.update(client));
		assertEquals("Formato do cpf inválido, favor utilizar o formato: 000.000.000-00", 
				exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza com cpf vazio")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void updateCpfIsNullTest() {
		Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
		Telephone telephone = new Telephone(3, "(00)00000-0000");
		Client cliente = new Client(3, "update" ,null, address, telephone);
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.update(cliente));
		assertEquals("Cpf do cliente está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar com cpf já existente")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void updateCpfDuplicateTest() {
			Address address = new Address(3, "rua 1", "Bairro 1", new City(3, "cidade 1", "UF"));
			Telephone telephone = new Telephone(3, "(00)00000-0000");
			Client client = new Client(4, "update" ,"100.100.100-11", address, telephone);
			var exception = assertThrows(IntegrityViolation.class, 
					() -> service.update(client));
			assertEquals("O cliente já contém o cpf 100.100.100-11", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void deleteTest() {
		List<Client> list = service.findAll();
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
		assertEquals("Id: 10 do cliente não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por cpf")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByCpfTest() {
		Client client = service.findByCpf("100.100.100-11");
		assertEquals(3, client.getId());
		assertEquals("Alex", client.getName());
		assertEquals(3, client.getAddress().getId());
		assertEquals(3, client.getTelephone().getId());
	}
	
	@Test
	@DisplayName("Teste buscar por cpf inexistente")
	void findByCpfNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByCpf("999.888.100-11"));
		assertEquals("cpf: 999.888.100-11 não encontrado no cliente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por endereço")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByAddressTest() {
		Address address = new Address(3, null, null, null);
		List<Client> list = service.findByAddressOrderByName(address);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por endereço nenhum encontrado")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByAddresNotFoundTest() {
		Address address = new Address(10, null, null, null);
		var exception = assertThrows(ObjectNotFound.class,
				() -> service.findByAddressOrderByName(address));
		assertEquals("O endereço: 10 do cliente não foi encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por telefone")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByTelephoneTest() {
		Telephone telephone = new Telephone(3, null);
		List<Client> list = service.findByTelephoneOrderByName(telephone);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por telefone nenhum encontrado")
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByTelephoneNotFoundTest() {
		Telephone telephone = new Telephone(10, null);
		var exception = assertThrows(ObjectNotFound.class,
				() -> service.findByTelephoneOrderByName(telephone));
		assertEquals("O telefone: 10 do cliente não foi encontrado", exception.getMessage());
	}
}
