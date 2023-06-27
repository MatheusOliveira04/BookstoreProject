package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Author;
import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.models.BookAuthor;
import br.com.trier.bookstore.bookstore.repositories.BookAuthorRepository;
import br.com.trier.bookstore.bookstore.services.BookAuthorService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {

	@Autowired
	BookAuthorRepository repository;

	private void validBook(BookAuthor bookAuthor) {
		if(bookAuthor == null) {
			throw new IntegrityViolation("Livro está vazio");
		}
	}
	
	@Override
	public List<BookAuthor> findAll() {
		List<BookAuthor> list = repository.findAll();
		if(list.isEmpty()){
			throw new ObjectNotFound("Nenhum livro de autor encontrado");
		}
		return list;
	}

	@Override
	public BookAuthor findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s do livro do autor não encontrado"));
	}

	@Override
	public BookAuthor insert(BookAuthor bookAuthor) {
		validBook(bookAuthor);
		return repository.save(bookAuthor);
	}

	@Override
	public BookAuthor update(BookAuthor bookAuthor) {
		findById(bookAuthor.getId());
		validBook(bookAuthor);
		return repository.save(bookAuthor);
	}

	@Override
	public void delete(Integer id) {
		BookAuthor bookAuthor = findById(id);
		repository.delete(bookAuthor);
		
	}

	@Override
	public Optional<BookAuthor> findByAuthor(Author author) {
		Optional<BookAuthor> bookAuthor = repository.findByAuthor(author);
		if(bookAuthor.isEmpty()){
			throw new ObjectNotFound("Nenhum autor encontrado");
		}
		return bookAuthor;
	}

	@Override
	public Optional<BookAuthor> findByBook(Book book) {
		Optional<BookAuthor> bookAuthor = repository.findByBook(book);
		if(bookAuthor.isEmpty()){
			throw new ObjectNotFound("Nenhum livro encontrado");
		}
		return bookAuthor;
	}
}
