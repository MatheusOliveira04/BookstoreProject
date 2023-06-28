package br.com.trier.bookstore;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.bookstore.bookstore.BookstoreApplication;
import br.com.trier.bookstore.bookstore.services.AddressService;
import br.com.trier.bookstore.bookstore.services.AuthorService;
import br.com.trier.bookstore.bookstore.services.BookAuthorService;
import br.com.trier.bookstore.bookstore.services.BookService;
import br.com.trier.bookstore.bookstore.services.CityService;
import br.com.trier.bookstore.bookstore.services.ClientService;
import br.com.trier.bookstore.bookstore.services.GenreBookService;
import br.com.trier.bookstore.bookstore.services.GenreService;
import br.com.trier.bookstore.bookstore.services.SaleService;
import br.com.trier.bookstore.bookstore.services.SalespersonService;
import br.com.trier.bookstore.bookstore.services.TelephoneService;
import br.com.trier.bookstore.bookstore.services.impl.AddressServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.AuthorServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.BookAuthorServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.BookServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.CityServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.ClientServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.GenreBookServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.GenreServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.SaleServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.SalespersonServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.TelephoneServiceImpl;

@TestConfiguration
@SpringBootTest(classes = BookstoreApplication.class)
@ActiveProfiles("test")
public class BaseTest {

	@Bean
	public CityService cityService() {
		return new CityServiceImpl();
	}
	
	@Bean
	public AddressService addressService() {
		return new AddressServiceImpl();
	}
	
	@Bean
	public TelephoneService telephoneService() {
		return new TelephoneServiceImpl();
	}
	
	@Bean
	public SalespersonService salespersonService() {
		return new SalespersonServiceImpl();
	}
	
	@Bean
	public ClientService clientService() {
		return new ClientServiceImpl();
	}
	
	@Bean
	public GenreService genreService() {
		return new GenreServiceImpl();
	}
	
	@Bean
	public BookService bookService() {
		return new BookServiceImpl();
	}
	
	@Bean
	public GenreBookService genreBookService() {
		return new GenreBookServiceImpl();
	}
	
	@Bean 
	public AuthorService authorService() {
		return new AuthorServiceImpl();
	}
	
	@Bean
	public BookAuthorService bookAuthorService() {
		return new BookAuthorServiceImpl(); 
	} 
	
	@Bean
	public SaleService saleService() {
		return new SaleServiceImpl();
	}
}
