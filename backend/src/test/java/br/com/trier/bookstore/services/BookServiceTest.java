package br.com.trier.bookstore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.bookstore.BaseTest;
import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.services.BookService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class BookServiceTest extends BaseTest{

	@Autowired
	BookService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/book.sql"})
	void findByIdTest() {
		Book book = service.findById(3);
		assertEquals("Mar", book.getName());
	}
	
	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 do livro não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/book.sql"})
	void findAllTest(){
		List<Book> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum livro encontrado", exception.getMessage());
	}
	@Test
	@DisplayName("Teste inserir")
	void insertTest() {
		Book book = new Book(1,"insert");
		book = service.insert(book);
		assertEquals("insert", book.getName());
	}
	

	@Test
	@DisplayName("Teste inserir com nome vazio")
	void insertNameEmptyTest() {
		Book book = new Book(1,null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(book));
		assertEquals("Nome do livro está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/book.sql"})
	void updateTest() {
		Book book = new Book(4,"update");
		book = service.update(book);
		assertEquals("update", book.getName());	
	}
	
	@Test
	@DisplayName("Teste atualiza com nome vazio")
	@Sql({"classpath:/resources/sqls/book.sql"})
	void updateNameEmptyTest() {
		Book book = new Book(3,null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(book));
		assertEquals("Nome do livro está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/book.sql"})
	void deleteTest() {
		List<Book> list = service.findAll();
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
		assertEquals("Id: 10 do livro não encontrado", exception.getMessage());
	}
}
