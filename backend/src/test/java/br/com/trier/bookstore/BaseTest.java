package br.com.trier.bookstore;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.bookstore.bookstore.BookstoreApplication;
import br.com.trier.bookstore.bookstore.services.AddressService;
import br.com.trier.bookstore.bookstore.services.CityService;
import br.com.trier.bookstore.bookstore.services.SalespersonService;
import br.com.trier.bookstore.bookstore.services.TelephoneService;
import br.com.trier.bookstore.bookstore.services.impl.AddressServiceImpl;
import br.com.trier.bookstore.bookstore.services.impl.CityServiceImpl;
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
	public SalespersonService salesperson() {
		return new SalespersonServiceImpl();
	}
}
