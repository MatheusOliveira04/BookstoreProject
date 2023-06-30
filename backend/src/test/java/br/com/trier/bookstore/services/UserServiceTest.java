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
import br.com.trier.bookstore.bookstore.models.User;
import br.com.trier.bookstore.bookstore.services.UserService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTest{
	
	@Autowired
	UserService service;
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByIdTest() {
		User usuario = service.findById(3);
		assertNotNull(usuario);
	}
	
	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByIdNonExistTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("O usuário 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void listAllTest() {
		List<User> list = service.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste listar todo usuários sem cadastro")
	void listAllEmptyTest() {
	var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
	assertEquals("Nenhum usuário cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	void insertTest() {
		var user = new User(1, "insert", "insert", "insert","ADMIN");
		user = service.insert(user);
		assertNotNull(user);
		assertEquals(1, user.getId());
		assertEquals("insert", user.getName());
		assertEquals("insert", user.getEmail());
		assertEquals("insert", user.getPassword());
	}
	
	@Test
	@DisplayName("Teste inserir usuário com email duplicado")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void insertUserDuplicatedEmailTest() {
		var user2 = new User(5, "Lucas" ,"matheus@gmail.com", "17777","ADMIN");
		var exception = assertThrows(IntegrityViolation.class,() -> service.insert(user2));
		assertEquals("Email já existe matheus@gmail.com", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void update() {
		var user = new User(3, "update", "update", "update","ADMIN");
		user = service.update(user);
		assertNotNull(user);
		assertEquals(3, user.getId());
		assertEquals("update", user.getName());
		assertEquals("update", user.getEmail());
		assertEquals("update", user.getPassword());
	}
	
	@Test
	@DisplayName("Teste atualiza id não encontrado")
	void updateIdNotFound() {
		var exception = assertThrows(ObjectNotFound.class, () ->
				service.update(new User(10, "update", "update", "update","ADMIN")));
		assertEquals("O usuário 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar email duplicado")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void updateEmailDuplicate() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(
		new User(4,"update","matheus@gmail.com","000","ADMIN")));
		assertEquals("Email já existe matheus@gmail.com", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void deleteTest() {
		List<User> list = service.listAll();
		assertEquals(2, list.size());
		service.delete(3);
		list = service.listAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar id não encontrado")
	void deleteIdNotFound() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("O usuário 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por nome começa com uma letra")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByNameStartingTest() {
		List<User> list = service.findBynameStartingWithIgnoreCase("u");
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por nome começa com uma letra, nenhum encontrado")
	void finByNameStartingEmpty() {
		var exception = assertThrows(ObjectNotFound.class, () ->
		service.findBynameStartingWithIgnoreCase("xz"));
		assertEquals("Nenhum nome de usúario contém xz", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por email")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByEmailTest() {
		var user = service.findByEmail("matheus@gmail.com");
		assertNotNull(user);
		assertEquals(3, user.get().getId());
		assertEquals("User 1", user.get().getName());
		assertEquals("1907", user.get().getPassword());
		assertEquals("ADMIN,USER", user.get().getRoles());
	}
	
	@Test
	@DisplayName("Teste buscar por email, nenhum encontrado")
	void findByEmailNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> 
		service.findByEmail("User12345@gmail.com"));
		assertEquals("Nenhum email: User12345@gmail.com encontrado no usuario", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por nome")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByNameTest() {
		var user = service.findByName("User 1");
		assertNotNull(user);
		assertEquals(3, user.get().getId());
		assertEquals("User 1", user.get().getName());
		assertEquals("matheus@gmail.com", user.get().getEmail());
		assertEquals("1907", user.get().getPassword());
		assertEquals("ADMIN,USER", user.get().getRoles());
	}
	
	@Test
	@DisplayName("Teste buscar por nome, nenhum encontrado")
	void findByNameNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> 
		service.findByName("User 011"));
		assertEquals("Nenhum nome: User 011 encontrado no usuario", exception.getMessage());
	}
}