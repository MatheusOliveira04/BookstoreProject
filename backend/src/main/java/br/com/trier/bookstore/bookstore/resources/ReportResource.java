package br.com.trier.bookstore.bookstore.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.Order;
import br.com.trier.bookstore.bookstore.models.Sale;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.dto.BookSoldBySalespersonDTO;
import br.com.trier.bookstore.bookstore.models.dto.ClientOfSalesperson;
import br.com.trier.bookstore.bookstore.models.dto.PersonDTO;
import br.com.trier.bookstore.bookstore.services.BookService;
import br.com.trier.bookstore.bookstore.services.ClientService;
import br.com.trier.bookstore.bookstore.services.OrderService;
import br.com.trier.bookstore.bookstore.services.SaleService;
import br.com.trier.bookstore.bookstore.services.SalespersonService;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@RestController
@RequestMapping("/find")
public class ReportResource {

	@Autowired
	SalespersonService salespersonService;

	@Autowired
	BookService bookService;
	
	@Autowired
	SaleService saleService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ClientService clientService;
	
	@Secured({"ROLE_USER"})
	@GetMapping("/book-sold-salesperson/{idSalesperson}")
	public ResponseEntity<BookSoldBySalespersonDTO> findBookBySalesperson(
			@PathVariable Integer idSalesperson){
		
		Salesperson salerperson = salespersonService.findById(idSalesperson);
		
		List<Sale> listSale = saleService.findBySalesperson(salerperson);
		
		
		List<Order> listOrder = listSale.stream()
                .flatMap(a -> {
                    try {
                        return orderService.findBySale(a).stream();
                    } catch (ObjectNotFound e) {
                        return Stream.empty();
                    }
                }).toList();
		
		Integer sumQuantityBook = listOrder.stream().mapToInt(Order::getQuantityBook).sum();
		
		List<Book> listBook = listOrder.stream()
		                .map(Order::getBook) 
		                .toList();
	 		 
		 return ResponseEntity.ok(new BookSoldBySalespersonDTO(salerperson.getName(), sumQuantityBook,
				 listBook));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/client-by-salesperson/{idSalesperson}")
	public ResponseEntity<ClientOfSalesperson> findClientBySalesperson(
			@PathVariable Integer idSalesperson){
		
		Salesperson salesperson = salespersonService.findById(idSalesperson);
		
		List<Sale> listSale = saleService.findBySalesperson(salesperson);
		
		List<PersonDTO> listClient = listSale
				.stream()
				.map(Sale::getClient).map(client -> client.toDTO())
				.toList();
		
		return ResponseEntity.ok(new ClientOfSalesperson(salesperson.getName(), listClient.size(),
				listClient));
	}
}
