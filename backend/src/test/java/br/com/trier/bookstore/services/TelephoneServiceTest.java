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
import br.com.trier.bookstore.bookstore.models.Telephone;
import br.com.trier.bookstore.bookstore.services.TelephoneService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class TelephoneServiceTest extends BaseTest {
	
	@Autowired
	TelephoneService service;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void findByIdTest() {
		Telephone telephone = service.findById(3);
		assertNotNull(telephone);
		assertEquals("(48)98765-4321", telephone.getNumber());
	}

	@Test
	@DisplayName("Teste buscar por id inexistente")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id: 10 do telefone não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void findAllTest(){
		List<Telephone> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos sem nenhum cadastro")
	void findAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum telefone encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	void insertTest() {
		Telephone telephone = new Telephone(1,"(48)98765-4321");
		telephone = service.insert(telephone);
		assertNotNull(telephone);
		assertEquals("(48)98765-4321", telephone.getNumber());
	}
	
	@Test
	@DisplayName("Teste inserir com número inválido")
	void insertNumberInvalidTest() {
		Telephone telephone = new Telephone(1,"45789876504321");
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.insert(telephone));
		assertEquals("Formato de número inválido, favor utilizar o formato: (00)00000-0000",
				exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza")
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void updateTest() {
		Telephone telephone = new Telephone(3,"(48)98765-4321");
		telephone = service.update(telephone);
		assertNotNull(telephone);
		assertEquals("(48)98765-4321", telephone.getNumber());
	}
	
	@Test
	@DisplayName("Teste atualiza com número inválido")
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void updateNumberInvalidTest() {
		Telephone telephone = new Telephone(3,"45789876504321");
		var exception = assertThrows(IntegrityViolation.class, 
				() -> service.update(telephone));
		assertEquals("Formato de número inválido, favor utilizar o formato: (00)00000-0000",
				exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza com id não encontrado")
	void updateIdNotFoundTest() {
		Telephone telephone = new Telephone(3,"(48)98765-4321");
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.update(telephone));
		assertEquals("Id: 3 do telefone não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void deleteTest() {
		List<Telephone> list = service.findAll();
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
		assertEquals("Id: 10 do telefone não encontrado", exception.getMessage());
	}
}
