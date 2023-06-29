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

import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.dto.PersonDTO;
import br.com.trier.bookstore.bookstore.services.AddressService;
import br.com.trier.bookstore.bookstore.services.SalespersonService;
import br.com.trier.bookstore.bookstore.services.TelephoneService;

@RestController
@RequestMapping("/salesperson")
public class SalespersonResource {

	@Autowired
	private SalespersonService service;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private TelephoneService telephoneService;
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<PersonDTO>> findAll(){
		return ResponseEntity.ok(service.findAll()
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<PersonDTO> findByCpf(@PathVariable String cpf){
		return ResponseEntity.ok(service.findByCpf(cpf).toDTO());
	}
	
	@GetMapping("/address/{idAddress}")
	public ResponseEntity<List<PersonDTO>> findByAddress(@PathVariable Integer idAddress){
		return ResponseEntity.ok(service.findByAddressOrderByName(addressService.findById(idAddress))
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	
	@GetMapping("/telephone/{idTelephone}")
	public ResponseEntity<List<PersonDTO>> findByTelephone(@PathVariable Integer idTelephone){
		return ResponseEntity.ok(service.findByTelephoneOrderByName(
				telephoneService.findById(idTelephone))
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	
	@PostMapping
	public ResponseEntity<PersonDTO> insert(@RequestBody PersonDTO dto){
		Salesperson salesperson = new Salesperson(
				dto, 
				addressService.findById(dto.getAddressDTO().getId()), 
				telephoneService.findById(dto.getTelephoneId()));
		return ResponseEntity.ok(service.insert(salesperson).toDTO());
	}
	@PutMapping("/{id}")
	public ResponseEntity<PersonDTO> update(@PathVariable Integer id, @RequestBody PersonDTO dto){
		Salesperson salesperson = new Salesperson(
				dto, 
				addressService.findById(dto.getAddressDTO().getId()), 
				telephoneService.findById(dto.getTelephoneId()));
		salesperson.setId(id);
		return ResponseEntity.ok(service.update(salesperson).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
		}
	
}
