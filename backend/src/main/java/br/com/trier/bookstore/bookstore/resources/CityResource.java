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

import br.com.trier.bookstore.bookstore.models.City;
import br.com.trier.bookstore.bookstore.services.CityService;

@RestController
@RequestMapping("/cidade")
public class CityResource {

	@Autowired
	CityService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<City> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<City>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<City> findByNameIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameIgnoreCase(name));
	}
	
	@PostMapping
	public ResponseEntity<City> insert(@RequestBody City city){
		return ResponseEntity.ok(service.insert(city));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<City> update(@PathVariable Integer id, @RequestBody City city){
		city.setId(id);
		return ResponseEntity.ok(service.update(city));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<City> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
		}
}
