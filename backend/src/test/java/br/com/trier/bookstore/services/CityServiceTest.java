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
import br.com.trier.bookstore.bookstore.models.City;
import br.com.trier.bookstore.bookstore.services.CityService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class CityServiceTest extends BaseTest{

	@Autowired
	CityService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void findByIdTest() {
		City city = service.findById(3);
		assertNotNull(city);
		assertEquals("Gravatal", city.getName());
		assertEquals("SC", city.getUf());
	}
	
	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 da cidade não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void findAllTest(){
		List<City> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhuma cidade encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	void insertTest() {
		City city = new City(1, "Gravatal", "SC");
		city = service.insert(city);
		assertNotNull(city);
		assertEquals(1, city.getId());
		assertEquals("Gravatal", city.getName());
		assertEquals("SC", city.getUf());
	}
	
	@Test
	@DisplayName("Teste inserir com nome vazio")
	void insertNameEmptyTest() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.insert(new City(1, null , "SC")));
		assertEquals("Nome da cidade está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir com nome duplicado")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void insertNameDuplicateTest() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.insert(new City(4,"Gravatal", "SC")));
		assertEquals("A cidade já contém o nome Gravatal", exception.getMessage()); 
	}
	
	@Test
	@DisplayName("Teste inserir Uf que não contém dois caracteres")
	void insertUfInvalidTest() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.insert(new City(1,"Gravatal", "SSS")));
		assertEquals("Uf da cidade deve conter 2 caracteres", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir com Uf vazio")
	void insertUfEmptyTeste() {
		City city = new City(1,"Gravatal"," ");
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.insert(city));
		assertEquals("Uf da cidade deve conter 2 caracteres", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void updateTest() {
		City city = new City(3, "update", "up");
		city = service.update(city);
		assertNotNull(city);
		assertEquals(3, city.getId());
		assertEquals("update", city.getName());
		assertEquals("up", city.getUf());
	}
	
	@Test
	@DisplayName("Teste atualiza com nome vazio")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void updateNameEmptyTest() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(new City(3, null , "SC")));
		assertEquals("Nome da cidade está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza com nome duplicado")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void updateNameDuplicateTest() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(new City(4,"Gravatal", "SC")));
		assertEquals("A cidade já contém o nome Gravatal", exception.getMessage()); 
	}
	
	@Test
	@DisplayName("Teste atualiza Uf que não contém dois caracteres")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void updateUfInvalidTest() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(new City(3,"Gravatal", "SSS")));
		assertEquals("Uf da cidade deve conter 2 caracteres", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza com Uf vazio")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void updateUfEmptyTest() {
		City city = new City(3,"Gravatal"," ");
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(city));
		assertEquals("Uf da cidade deve conter 2 caracteres", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void deleteTest() {
		List<City> list = service.findAll();
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
		assertEquals("Id: 10 da cidade não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por nome")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void findByNameIgnoreCaseTest() {
		City city = service.findByNameIgnoreCase("grAvAtAl");
		assertEquals(3, city.getId());
		assertEquals("Gravatal", city.getName());
		assertEquals("SC", city.getUf());
	}
	
	@Test
	@DisplayName("Teste buscar por nome inexistente")
	void findByNameNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByNameIgnoreCase("teste"));
		assertEquals("nome: teste não encontrado na cidade", exception.getMessage());
	}
}
