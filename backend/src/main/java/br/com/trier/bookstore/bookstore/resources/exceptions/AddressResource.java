package br.com.trier.bookstore.bookstore.resources.exceptions;

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

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.dto.AddressDTO;
import br.com.trier.bookstore.bookstore.services.AddressService;
import br.com.trier.bookstore.bookstore.services.CityService;

@RestController
@RequestMapping("/endereco")
public class AddressResource {

	@Autowired
	AddressService service;
	
	@Autowired
	CityService cityService;
	
	@GetMapping("/{id}")
	public ResponseEntity<AddressDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<AddressDTO>> findAll(){
		return ResponseEntity.ok(service.findAll()
				.stream()
				.map(address -> address.toDTO())
				.toList());
	}
	
	@GetMapping("/city/{idCity}")
	public ResponseEntity<List<AddressDTO>> findByCity(@PathVariable Integer idCity){
		return ResponseEntity.ok(service.findByCity(cityService.findById(idCity))
				.stream()
				.map(address -> address.toDTO())
				.toList());
	}
	
	@PostMapping
	public ResponseEntity<AddressDTO> insert(@RequestBody AddressDTO dto){
		Address address = new Address(dto, cityService.findById(dto.getCityId()));
		return ResponseEntity.ok(service.insert(address).toDTO());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AddressDTO> update(@PathVariable Integer id, @RequestBody AddressDTO dto){
		Address address = new Address(dto, cityService.findById(dto.getCityId()));	
		address.setId(id);
		return ResponseEntity.ok(service.update(address).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
