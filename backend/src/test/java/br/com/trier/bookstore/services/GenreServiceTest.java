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
import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Genre;
import br.com.trier.bookstore.bookstore.services.GenreService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class GenreServiceTest extends BaseTest{

	@Autowired
	GenreService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	void findByIdTest() {
		Genre genre = service.findById(3);
		assertNotNull(genre);
		assertEquals(3, genre.getId());
		assertEquals("Aventura", genre.getDescription());
	}
	
	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 do gênero não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	void insertTest() {
		Genre genre = new Genre(1, "insert");
		genre = service.insert(genre);
		assertNotNull(genre);
		assertEquals(1, genre.getId());
		assertEquals("insert", genre.getDescription());
	}
	
	@Test
	@DisplayName("Teste inserir com descrição vazia")
	void insertDescriptionEmptyTest() {
		Genre genre = new Genre(1, null);
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.insert(genre));
		assertEquals("Descrição do gênero está vazio", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir com descrição já existente")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	void insertDescriptionAlreadtyExistsTest() {
		Genre genre = new Genre(5, "Aventura");
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.insert(genre));
		assertEquals("O gênero já contém o nome Aventura", exception.getMessage());
	}

	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	void updateTest() {
		Genre genre = new Genre(3, "update");
		genre = service.update(genre);
		assertNotNull(genre);
		assertEquals(3, genre.getId());
		assertEquals("update", genre.getDescription());
	}
	
	@Test
	@DisplayName("Teste atualizar com id inexistente")
	void updateIdNotFound() {
		Genre genre = new Genre(10, "update");
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.update(genre));
		assertEquals("Id: 10 do gênero não encontrado", exception.getMessage());
	
	}
	
	@Test
	@DisplayName("Teste atualizar com descrição vazia")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	void updateDescriptionEmptyTest() {
		Genre genre = new Genre(3, null);
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.update(genre));
		assertEquals("Descrição do gênero está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar com descrição já existente")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	void updateDescriptionAlreadtyExistsTest() {
		Genre genre = new Genre(4, "Aventura");
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.update(genre));
		assertEquals("O gênero já contém o nome Aventura", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	void deleteTest() {
		List<Genre> list = service.findAll();
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
		assertEquals("Id: 10 do gênero não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por descrição")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	void findByDescriptionTest() {
		Genre genre = service.findByDescritpionIgnoreCase("Aventura");
		assertNotNull(genre);
		assertEquals(3, genre.getId());
	}
	
	@Test
	@DisplayName("Teste buscar por descrição não encontrado")
	void findByDescriptionNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByDescritpionIgnoreCase("Test"));
		assertEquals("Gênero: Test não encontrado", exception.getMessage());
	}
}
