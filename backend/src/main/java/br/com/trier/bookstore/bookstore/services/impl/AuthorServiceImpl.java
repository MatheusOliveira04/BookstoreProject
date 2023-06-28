package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Author;
import br.com.trier.bookstore.bookstore.repositories.AuthorRepository;
import br.com.trier.bookstore.bookstore.services.AuthorService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class AuthorServiceImpl implements AuthorService {
	
	@Autowired
	AuthorRepository repository;

	private void validName(Author author) {
		if(author.getName() == null || author.getName().trim().isEmpty()) {
			throw new IntegrityViolation("Nome do autor está vazio");
		}
	}
	
	@Override
	public List<Author> findAll() {
		List<Author> list = repository.findAll();
		if(list.isEmpty()){
			throw new ObjectNotFound("Nenhum autor encontrado");
		}
		return list;
	}

	@Override
	public Author findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s do autor não encontrado".formatted(id)));
	}

	@Override
	public Author insert(Author author) {
		validName(author);
		return repository.save(author);
	}

	@Override
	public Author update(Author author) {
		findById(author.getId());
		validName(author);
		return repository.save(author);
	}

	@Override
	public void delete(Integer id) {
		Author author = findById(id);
		repository.delete(author);
		
	}

}
