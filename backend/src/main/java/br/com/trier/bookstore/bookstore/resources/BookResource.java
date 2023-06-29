package br.com.trier.bookstore.bookstore.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.services.BookService;

@RestController
@RequestMapping("/book")
public class BookResource {

	@Autowired
	BookService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Book>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<Book> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name).get());
	}
	
	@PostMapping
	public ResponseEntity<Book> insert(@RequestBody Book book){
		return ResponseEntity.ok(service.insert(book));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Book> update(@PathVariable Integer id, @RequestBody Book book){
		book.setId(id);
		return ResponseEntity.ok(service.update(book));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
		}
}
