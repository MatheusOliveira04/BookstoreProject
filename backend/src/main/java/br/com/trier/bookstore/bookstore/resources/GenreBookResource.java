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

import br.com.trier.bookstore.bookstore.models.GenreBook;
import br.com.trier.bookstore.bookstore.models.dto.GenreBookDTO;
import br.com.trier.bookstore.bookstore.services.BookService;
import br.com.trier.bookstore.bookstore.services.GenreBookService;
import br.com.trier.bookstore.bookstore.services.GenreService;

@RestController
@RequestMapping("/genre-book")
public class GenreBookResource {

	@Autowired
	GenreBookService service;

	@Autowired
	BookService bookService;
	
	@Autowired
	GenreService genreService;
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<GenreBookDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<GenreBookDTO>> findAll(){
		return ResponseEntity.ok(service.findAll()
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/book/{idBook}")
	public ResponseEntity<List<GenreBookDTO>> findByBook(@PathVariable Integer idBook){
		return ResponseEntity.ok(service.findByBook(bookService.findById(idBook))
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/genre/{idGenre}")
	public ResponseEntity<List<GenreBookDTO>> findByGenre(@PathVariable Integer idGenre){
		return ResponseEntity.ok(service.findByGenre(genreService.findById(idGenre))
				.stream()
				.map(x -> x.toDTO())
				.toList());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<GenreBookDTO> insert(@RequestBody GenreBookDTO dto){
		GenreBook genreBook = new GenreBook(dto,
				bookService.findById(dto.getBookId()),
				genreService.findById(dto.getGenreId()));
		return ResponseEntity.ok(service.insert(genreBook).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<GenreBookDTO> update(
			@PathVariable Integer id, 
			@RequestBody GenreBookDTO dto){
		GenreBook genreBook = new GenreBook(dto,
				bookService.findById(dto.getBookId()),
				genreService.findById(dto.getGenreId()));
		genreBook.setId(id);
		return ResponseEntity.ok(service.update(genreBook).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
		}
}
