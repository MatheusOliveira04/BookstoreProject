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
import br.com.trier.bookstore.bookstore.models.Author;
import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.BookAuthor;
import br.com.trier.bookstore.bookstore.services.BookAuthorService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class BookAuthorServiceTest extends BaseTest{

	@Autowired
	BookAuthorService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/author.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/book_author.sql"})
	void findByIdTest() {
		BookAuthor bookAuthor = service.findById(3);
		assertNotNull(bookAuthor);
		assertEquals(3, bookAuthor.getBook().getId());
		assertEquals("Mar", bookAuthor.getBook().getName());
		assertEquals(3, bookAuthor.getAuthor().getId());
		assertEquals("Max", bookAuthor.getAuthor().getName());
	}
	
	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 do livro do autor não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/author.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/book_author.sql"})
	void findAllTest(){
		List<BookAuthor> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum livro de autor encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	@Sql({"classpath:/resources/sqls/author.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void insertTest() {
		var bookAuthor = new BookAuthor(1, new Book(3, null), new Author(3, null));
		bookAuthor = service.insert(bookAuthor);
		assertNotNull(service);
		assertEquals(3, bookAuthor.getBook().getId());
		assertEquals(3, bookAuthor.getAuthor().getId());
	}
	
	@Test
	@DisplayName("Teste inserir com livro nulo")
	@Sql({"classpath:/resources/sqls/author.sql"})
	void insertBookNullTest() {
		var bookAuthor = new BookAuthor(1, null , new Author(3, null));
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(bookAuthor));
		assertEquals("Livro está vazio", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir com autor nulo")
	@Sql({"classpath:/resources/sqls/book.sql"})
	void insertAutorNullTest() {
		var bookAuthor = new BookAuthor(1, new Book(3, null), null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(bookAuthor));
		assertEquals("Autor está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/author.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/book_author.sql"})
	void updateTest() {
		var bookAuthor = new BookAuthor(4, new Book(3, null), new Author(3, null));
		bookAuthor = service.update(bookAuthor);
		assertNotNull(service);
		assertEquals(3, bookAuthor.getBook().getId());
		assertEquals(3, bookAuthor.getAuthor().getId());
	}
	
	@Test
	@DisplayName("Teste atualizar com livro nulo")
	@Sql({"classpath:/resources/sqls/author.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/book_author.sql"})
	void updateBookNullTest() {
		var bookAuthor = new BookAuthor(4, null , new Author(3, null));
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(bookAuthor));
		assertEquals("Livro está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar com autor nulo")
	@Sql({"classpath:/resources/sqls/author.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/book_author.sql"})
	void updateAutorNullTest() {
		var bookAuthor = new BookAuthor(4, new Book(3, null), null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(bookAuthor));
		assertEquals("Autor está vazio", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por livro")
	@Sql({"classpath:/resources/sqls/author.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/book_author.sql"})
	void findByBookTest() {
		Book book = new Book(3, null);
		List<BookAuthor> bookAuthor = service.findByBook(book);
		assertNotNull(bookAuthor);
		assertEquals(2, bookAuthor.size());
		
	}
	
	@Test
	@DisplayName("Teste buscar por livro todos sem nenhum cadastro")
	void findBookAllEmptyTest() {
		Book book = new Book(4, null);
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByBook(book));
		assertEquals("Nenhum livro encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar por autor")
	@Sql({"classpath:/resources/sqls/author.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/book_author.sql"})
	void findByAuthorTest() {
		Author author = new Author(3, null);
		List<BookAuthor> bookAuthor = service.findByAuthor(author);
		assertNotNull(bookAuthor);
		assertEquals(2, bookAuthor.size());
	}
	
	@Test
	@DisplayName("Teste buscar por autor todos sem nenhum cadastro")
	void findAuthorAllEmptyTest() {
		Author author = new Author(4, null);
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByAuthor(author));
		assertEquals("Nenhum autor encontrado", exception.getMessage());
	}
}
