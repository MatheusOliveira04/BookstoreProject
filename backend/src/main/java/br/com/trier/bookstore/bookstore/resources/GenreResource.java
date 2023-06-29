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

import br.com.trier.bookstore.bookstore.models.Genre;
import br.com.trier.bookstore.bookstore.services.GenreService;

@RestController
@RequestMapping("/genre")
public class GenreResource {

	@Autowired
	GenreService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Genre> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Genre>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/description/{description}")
	public ResponseEntity<Genre> findById(@PathVariable String description){
		return ResponseEntity.ok(service.findByDescritpionIgnoreCase(description));
	}
	
	@PostMapping
	public ResponseEntity<Genre> insert(@RequestBody Genre genre){
		return ResponseEntity.ok(service.insert(genre));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Genre> update(@PathVariable Integer id, @RequestBody Genre genre){
		genre.setId(id);
		return ResponseEntity.ok(service.update(genre));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
		}
}
