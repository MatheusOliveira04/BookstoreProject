package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.Genre;
import br.com.trier.bookstore.bookstore.models.GenreBook;
import br.com.trier.bookstore.bookstore.repositories.GenreBookRepository;
import br.com.trier.bookstore.bookstore.services.GenreBookService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class GenreBookServiceImpl implements GenreBookService{
	
	@Autowired
	GenreBookRepository repository;

	private void validBook(GenreBook genreBook) {
		if(genreBook.getBook() == null) {
			throw new IntegrityViolation("Livro está vazio");
		}
	}
	
	private void validGenre(GenreBook genreBook) {
		if(genreBook.getGenre() == null) {
			throw new IntegrityViolation("Gênero está vazio");
		}
	}
	
	
	@Override
	public List<GenreBook> findAll() {
		List<GenreBook> list = repository.findAll();
		if(list.isEmpty()){
			throw new ObjectNotFound("Nenhum gênero livro encontrado");
		}
		return list;
	}

	@Override
	public GenreBook findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s do gênero livro não encontrado".formatted(id)));
	}

	@Override
	public GenreBook insert(GenreBook genreBook) {
		validBook(genreBook);
		validGenre(genreBook);
		return repository.save(genreBook);
	}

	@Override
	public GenreBook update(GenreBook genreBook) {
		findById(genreBook.getId());
		validBook(genreBook);
		validGenre(genreBook);
		return repository.save(genreBook);
	}

	@Override
	public void delete(Integer id) {
		GenreBook genreBook = findById(id);
		repository.delete(genreBook);
		
	}

	@Override
	public List<GenreBook> findByBook(Book book) {
		List<GenreBook> genreBook = repository.findByBook(book);
		if(genreBook.isEmpty()){
			throw new ObjectNotFound("Nenhum livro encontrado");
		}
		return genreBook;
	}

	@Override
	public List<GenreBook> findByGenre(Genre genre) {
		List<GenreBook> genreBook = repository.findByGenre(genre);
		if(genreBook.isEmpty()){
			throw new ObjectNotFound("Nenhum gênero encontrado");
		}
		return genreBook;
	}

}
