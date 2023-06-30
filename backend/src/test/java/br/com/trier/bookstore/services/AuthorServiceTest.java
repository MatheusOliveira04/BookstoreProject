package br.com.trier.bookstore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.bookstore.BaseTest;
import br.com.trier.bookstore.bookstore.models.Author;
import br.com.trier.bookstore.bookstore.services.AuthorService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class AuthorServiceTest extends BaseTest{
	
	@Autowired
	AuthorService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/author.sql"})
	void findByIdTest() {
		var author = service.findById(3);
		assertNotNull(service);
		assertEquals("Max", author.getName());
	}

	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 do autor não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/author.sql"})
	void findAllTest(){
		List<Author> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum autor encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	void insertTest() {
		var author = new Author(1, "insert");
		author = service.insert(author);
		assertNotNull(author);
		assertEquals("insert", author.getName());
	}
	
	@Test
	@DisplayName("Teste inserir com nome vazio")
	void insertNameEmptyTest() {
		var author = new Author(1, null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(author));
		assertEquals("Nome do autor está vazio", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir com nome duplicado")
	@Sql({"classpath:/resources/sqls/author.sql"})
	void insertNameDuplicadedTest() {
		var author = new Author(5, "Max");
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(author));
		assertEquals("O autor já contém o nome Max", exception.getMessage());
	}

	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/author.sql"})
	void updateTest() {
		var author = new Author(3, "update");
		author = service.update(author);
		assertNotNull(author);
		assertEquals("update", author.getName());
	}
	
	@Test
	@DisplayName("Teste atualiza com id inexistente")
	void updateIdNotFoundTest() {
		var author = new Author(10, null);
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(author));
		assertEquals("Id: 10 do autor não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza com nome vazio")
	@Sql({"classpath:/resources/sqls/author.sql"})
	void updateNameEmptyTest() {
		var author = new Author(3, null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(author));
		assertEquals("Nome do autor está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza com nome duplicado")
	@Sql({"classpath:/resources/sqls/author.sql"})
	void updateNameDuplicadedTest() {
		var author = new Author(4, "Max");
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(author));
		assertEquals("O autor já contém o nome Max", exception.getMessage());
	}

	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/author.sql"})
	void deleteTest() {
		List<Author> list = service.findAll();
		assertEquals(2, list.size());
		service.delete(3);
		list = service.findAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar id inexistente")
	@Sql({"classpath:/resources/sqls/author.sql"})
	void deleteIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.delete(10));
		assertEquals("Id: 10 do autor não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("buscar por nome")
	@Sql({"classpath:/resources/sqls/author.sql"})
	void findByNameTest() {
		Optional<Author> autor = service.findByName("Max");
		assertNotNull(autor);
		assertEquals(3, autor.get().getId());
		assertEquals("Max", autor.get().getName());
	}
	
	@Test
	@DisplayName("buscar por nome nenhum encontrado")
	void findByNameNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByName("test"));
		assertEquals("Nenhum autor contém o nome: test", exception.getMessage());
	}
}
