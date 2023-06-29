package br.com.trier.bookstore.bookstore.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.bookstore.bookstore.models.Order;
import br.com.trier.bookstore.bookstore.models.dto.OrderDTO;
import br.com.trier.bookstore.bookstore.services.BookService;
import br.com.trier.bookstore.bookstore.services.OrderService;
import br.com.trier.bookstore.bookstore.services.SaleService;

@RestController
@RequestMapping("/order")
public class OrderResource {

	@Autowired
	private OrderService service;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private SaleService saleService;
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<OrderDTO>> findAll(){
		return ResponseEntity.ok(service.findAll()
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/book/{id}")
	public ResponseEntity<List<OrderDTO>> findByBook(@PathVariable Integer id){
		return ResponseEntity.ok(service.findByBook(bookService.findById(id))
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/sale/{id}")
	public ResponseEntity<List<OrderDTO>> findBySale(@PathVariable Integer id){
		return ResponseEntity.ok(service.findBySale(saleService.findById(id))
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<OrderDTO> insert(@RequestBody OrderDTO dto){
		Order order = new Order(dto, bookService.findById(dto.getBookId()),
				saleService.findById(dto.getSale().getId()));
		return ResponseEntity.ok(service.insert(order).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<OrderDTO> update(@PathVariable Integer id, @RequestBody OrderDTO dto){
		Order order = new Order(dto, bookService.findById(dto.getBookId()),
				saleService.findById(dto.getSale().getId()));
		order.setId(id);
		return ResponseEntity.ok(service.update(order).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
		}
}
