package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Book;
import br.com.trier.bookstore.bookstore.repositories.BookRepository;
import br.com.trier.bookstore.bookstore.services.BookService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookRepository repository;

	private void validName(Book book) {
		if(book.getName() == null || book.getName().trim().isEmpty()) {
			throw new IntegrityViolation("Nome do livro está vazio");
		}
	}
	
	@Override
	public List<Book> findAll() {
		List<Book> list = repository.findAll();
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum livro encontrado");
		}
		return list;
	}

	@Override
	public Book findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s do livro não encontrado".formatted(id)));
	}

	@Override
	public Book insert(Book book) {
		validName(book);
		return repository.save(book);
	}

	@Override
	public Book update(Book book) {
		findById(book.getId());
		validName(book);
		return repository.save(book);
	}

	@Override
	public void delete(Integer id) {
		Book book = findById(id);
		repository.delete(book);
		
	}
}
