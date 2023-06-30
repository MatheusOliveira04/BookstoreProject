package br.com.trier.bookstore.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.bookstore.bookstore.BookstoreApplication;
import br.com.trier.bookstore.bookstore.config.jwt.LoginDTO;
import br.com.trier.bookstore.bookstore.models.dto.BookSoldBySalespersonDTO;
import br.com.trier.bookstore.bookstore.models.dto.ClientOfSalesperson;

@ActiveProfiles("test")
@SpringBootTest(classes = BookstoreApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReportResourceTest {

	@Autowired
	TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String password) {
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> request = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> response = rest.exchange("/auth/token", 
				HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		HttpHeaders headers2 = new HttpHeaders();
		headers2.setBearerAuth(response.getBody());
		return headers2;
	}
	
	public ResponseEntity<BookSoldBySalespersonDTO> getBookBySalesperson(String url){
		return rest.exchange(url, HttpMethod.GET, 
				new HttpEntity<>(getHeaders("matheus@gmail.com", "1907")),
				BookSoldBySalespersonDTO.class);
	}
	
	public ResponseEntity<ClientOfSalesperson> getAllClientBySalesperson(String url){
		return rest.exchange(url, HttpMethod.GET, 
				new HttpEntity<>(getHeaders("matheus@gmail.com", "1907")),
				ClientOfSalesperson.class);
	}
	
	@Test
	@DisplayName("Teste buscar todos os livros vendidos por um vendedor")
	@Sql({"classpath:/resources/sqls/users.sql"})
	@Sql("classpath:/resources/sqls/telephone.sql")
	@Sql("classpath:/resources/sqls/city.sql")
	@Sql("classpath:/resources/sqls/address.sql")
	@Sql("classpath:/resources/sqls/client.sql")
	@Sql("classpath:/resources/sqls/salesperson.sql")
	@Sql("classpath:/resources/sqls/sale.sql")
	@Sql("classpath:/resources/sqls/book.sql")
	@Sql("classpath:/resources/sqls/order.sql")
	void findAllBooksBySalesperson() {
		ResponseEntity<BookSoldBySalespersonDTO> response = getBookBySalesperson(
				"/find/book-sold-salesperson/3");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar todos os livros vendidos por um vendedor")
	void findAllClientBySalesperson() {
		ResponseEntity<ClientOfSalesperson> response = getAllClientBySalesperson(
				"/find/client-by-salesperson/3");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
