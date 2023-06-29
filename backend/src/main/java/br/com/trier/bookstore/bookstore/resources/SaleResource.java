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

import br.com.trier.bookstore.bookstore.models.Sale;
import br.com.trier.bookstore.bookstore.models.dto.SaleDTO;
import br.com.trier.bookstore.bookstore.services.ClientService;
import br.com.trier.bookstore.bookstore.services.SaleService;
import br.com.trier.bookstore.bookstore.services.SalespersonService;

@RestController
@RequestMapping("/sale")
public class SaleResource {

	@Autowired
	SaleService service;

	@Autowired
	ClientService clientService;
	
	@Autowired
	SalespersonService salespersonService;
	
	@GetMapping("/{id}")
	public ResponseEntity<SaleDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<SaleDTO>> findAll(){
		return ResponseEntity.ok(service.findAll()
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	

	@GetMapping("/salesperson/{id}")
	public ResponseEntity<List<SaleDTO>> findByAuthor(@PathVariable Integer id){
		return ResponseEntity.ok(service.findBySalesperson(salespersonService.findById(id))
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	
	@GetMapping("/client/{id}")
	public ResponseEntity<List<SaleDTO>> findByClient(@PathVariable Integer id){
		return ResponseEntity.ok(service.findByClient(clientService.findById(id))
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	
	@PostMapping
	public ResponseEntity<SaleDTO> insert(@RequestBody SaleDTO dto){
		Sale sale = new Sale(dto, clientService.findById(dto.getClientId()), 
				salespersonService.findById(dto.getSalespersonId()));
		return ResponseEntity.ok(service.insert(sale).toDTO());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SaleDTO> update(@PathVariable Integer id, @RequestBody SaleDTO dto){
		Sale sale = new Sale(dto, clientService.findById(dto.getClientId()), 
				salespersonService.findById(dto.getSalespersonId()));
		sale.setId(id);
		return ResponseEntity.ok(service.update(sale).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
		}
}
