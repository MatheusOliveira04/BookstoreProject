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

import br.com.trier.bookstore.bookstore.models.Telephone;
import br.com.trier.bookstore.bookstore.services.TelephoneService;

@RestController
@RequestMapping("/telephone")
public class TelephoneResourceTest {

	@Autowired
	TelephoneService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Telephone> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Telephone>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Telephone> insert(@RequestBody Telephone telephone){
		return ResponseEntity.ok(service.insert(telephone));
	}
	@PutMapping("/{id}")
	public ResponseEntity<Telephone> update(@PathVariable Integer id, @RequestBody Telephone telephone){
		telephone.setId(id);
		return ResponseEntity.ok(service.update(telephone));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
		}
	
}
