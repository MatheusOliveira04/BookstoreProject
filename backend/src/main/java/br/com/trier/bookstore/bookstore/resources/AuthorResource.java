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

import br.com.trier.bookstore.bookstore.models.Author;
import br.com.trier.bookstore.bookstore.services.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorResource {

	@Autowired
	AuthorService service;
	

	@GetMapping("/{id}")
	public ResponseEntity<Author> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Author>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Author> insert(@RequestBody Author author){
		return ResponseEntity.ok(service.insert(author));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Author> update(@PathVariable Integer id, @RequestBody Author author){
		author.setId(id);
		return ResponseEntity.ok(service.update(author));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
		}
}
