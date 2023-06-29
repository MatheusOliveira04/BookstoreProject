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
import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.Genre;
import br.com.trier.bookstore.bookstore.models.GenreBook;
import br.com.trier.bookstore.bookstore.services.GenreBookService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class GenreBookServiceTest extends BaseTest{

	@Autowired
	GenreBookService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/genre_book.sql"})
	void findByIdTest() {
		GenreBook genreBook = service.findById(3);
		assertNotNull(genreBook);
		assertEquals(3, genreBook.getBook().getId());
		assertEquals(3, genreBook.getGenre().getId());
	}
	
	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 do gênero livro não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/genre_book.sql"})
	void findAllTest(){
		List<GenreBook> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum gênero livro encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void insertTest() {
		GenreBook genreBook = new GenreBook(1, new Book(3, null), new Genre(3, null));
		genreBook = service.insert(genreBook);
		assertNotNull(genreBook);
		assertEquals("Mar", genreBook.getBook().getName());
		assertEquals("Aventura", genreBook.getGenre().getDescription());
	}
	
	@Test
	@DisplayName("Teste inserir com livro vazio")
	void insertBookEmptyTest() {
		GenreBook genreBook = new GenreBook(1, null, new Genre(3, null));
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(genreBook));
		assertEquals("Livro está vazio", exception.getMessage());
	
	}
	
	@Test
	@DisplayName("Teste inserir com gênero vazio")
	void insertGenderEmptyTest() {
		GenreBook genreBook = new GenreBook(1, new Book(3, null), null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(genreBook));
		assertEquals("Gênero está vazio", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/genre_book.sql"})
	void updateTest() {
		GenreBook genreBook = new GenreBook(3, new Book(4, null), new Genre(4, null));
		genreBook = service.update(genreBook);
		assertNotNull(genreBook);
		assertEquals("Sol", genreBook.getBook().getName());
		assertEquals("Terror", genreBook.getGenre().getDescription());
	}
	
	@Test
	@DisplayName("Teste atualizar id não encontrado")
	void updateIdNotFoundTest() {
		GenreBook genreBook = new GenreBook(10, new Book(4, null), new Genre(4, null));
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(genreBook));
		assertEquals("Id: 10 do gênero livro não encontrado", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste atualizar com livro vazio")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/genre_book.sql"})
	void updateBookEmptyTest() {
		GenreBook genreBook = new GenreBook(3, null, new Genre(4, null));
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(genreBook));
		assertEquals("Livro está vazio", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste atualizar com gênero vazio")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/genre_book.sql"})
	void updateGenderEmptyTest() {
		GenreBook genreBook = new GenreBook(3, new Book(4, null), null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(genreBook));
		assertEquals("Gênero está vazio", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/genre_book.sql"})
	void deleteTest() {
		List<GenreBook> list = service.findAll();
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
		assertEquals("Id: 10 do gênero livro não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por livro")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/genre_book.sql"})
	void findByBookTest() {
		Book book = new Book(3,null);
		List<GenreBook> list = service.findByBook(book);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por livro nenhum encontrado")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/genre_book.sql"})
	void findByBookNotFoundTest() {
		Book book = new Book(10,null);
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByBook(book));
		assertEquals("Nenhum livro encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por gênero")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/genre_book.sql"})
	void findByGenreTest() {
		Genre genre = new Genre(3,null);
		List<GenreBook> list = service.findByGenre(genre);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por gênero nenhum encontrado")
	@Sql({"classpath:/resources/sqls/genre.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/genre_book.sql"})
	void findByGenreNotFoundTest() {
		Genre genre = new Genre(10,null);
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByGenre(genre));
		assertEquals("Nenhum gênero encontrado", exception.getMessage());
	}
}
