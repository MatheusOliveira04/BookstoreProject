package br.com.trier.bookstore.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpsRedirectSpec;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.bookstore.bookstore.BookstoreApplication;
import br.com.trier.bookstore.bookstore.config.jwt.LoginDTO;
import br.com.trier.bookstore.bookstore.models.dto.AddressDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = BookstoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressResourceTest {

	@Autowired
	TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String senha) {
		LoginDTO loginDTO = new LoginDTO(email, senha);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> request = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> response = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,
				request,
				String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		HttpHeaders headers2 = new HttpHeaders();
		headers2.setBearerAuth(response.getBody());
		return headers2;
	}
	
	private ResponseEntity<AddressDTO> getAddress(String url){
		return rest.exchange(url, HttpMethod.GET,
				new HttpEntity<>(getHeaders("matheus@gmail.com", "1907")),
				AddressDTO.class);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<List<AddressDTO>> getAllAddress(String url){
		return rest.exchange(url, HttpMethod.GET,
				new HttpEntity<>(getHeaders("matheus@gmail.com", "1907")),
				new ParameterizedTypeReference<List<AddressDTO>>() {} );
	}
	
	@Test
	@DisplayName("Teste buscar por id")
	void findByIdTest() {
		ResponseEntity<AddressDTO> response = getAddress("/address/3");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/delete_columns.sql"})
	@Sql({"classpath:/resources/sqls/users.sql"})
	@Sql({"classpath:/resources/sqls/city.sql"})
	void findByIdNotFoundTest() {
		ResponseEntity<AddressDTO> response = getAddress("/address/10");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/delete_columns.sql"})
	@Sql({"classpath:/resources/sqls/users.sql"})
	@Sql({"classpath:/resources/sqls/city.sql"})
	@Sql({"classpath:/resources/sqls/address.sql"})
	void findAllTest() {
		ResponseEntity<List<AddressDTO>> response = getAllAddress("/address");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(2, response.getBody().size());
	}
	
	@Test
	@DisplayName("Teste inserir")
	@Sql({"classpath:/resources/sqls/delete_users.sql"})
	@Sql({"classpath:/resources/sqls/users.sql"})
	@Sql({"classpath:/resources/sqls/city.sql"})
	void insertTest() {
		AddressDTO addressDTO = new AddressDTO(1, "Rua", "Bairro", 1, "Orleans", "SC");
		HttpHeaders headers = getHeaders("matheus@gmail.com", "1907");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AddressDTO> request = new HttpEntity<>(addressDTO, headers);
		ResponseEntity<AddressDTO> response = rest.exchange("/address", 
				HttpMethod.POST, request, AddressDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
