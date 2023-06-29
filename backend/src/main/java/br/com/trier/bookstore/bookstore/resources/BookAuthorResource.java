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

import br.com.trier.bookstore.bookstore.models.BookAuthor;
import br.com.trier.bookstore.bookstore.models.dto.BookAuthorDTO;
import br.com.trier.bookstore.bookstore.services.AuthorService;
import br.com.trier.bookstore.bookstore.services.BookAuthorService;
import br.com.trier.bookstore.bookstore.services.BookService;

@RestController
@RequestMapping("/book-author")
public class BookAuthorResource {

	@Autowired
	private BookAuthorService service;

	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private BookService bookService;
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<BookAuthorDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<BookAuthorDTO>> findAll(){
		return ResponseEntity.ok(service.findAll()
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/author/{id}")
	public ResponseEntity<List<BookAuthorDTO>> findByAuthor(@PathVariable Integer id){
		return ResponseEntity.ok(service.findByAuthor(authorService.findById(id))
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/book/{id}")
	public ResponseEntity<List<BookAuthorDTO>> findByBook(@PathVariable Integer id){
		return ResponseEntity.ok(service.findByBook(bookService.findById(id))
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<BookAuthorDTO> insert(@RequestBody BookAuthorDTO dto){
		BookAuthor bookAuthor = new BookAuthor(dto, 
				bookService.findById(dto.getId()),
				authorService.findById(dto.getId()));
		return ResponseEntity.ok(service.insert(bookAuthor).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<BookAuthorDTO> update(
			@PathVariable Integer id, 
			@RequestBody BookAuthorDTO dto){
		BookAuthor bookAuthor = new BookAuthor(dto, 
				bookService.findById(dto.getId()),
				authorService.findById(dto.getId()));
		bookAuthor.setId(id);
		return ResponseEntity.ok(service.update(bookAuthor).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
		}
}
