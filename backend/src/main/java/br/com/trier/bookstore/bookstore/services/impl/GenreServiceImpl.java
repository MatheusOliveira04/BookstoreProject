package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Genre;
import br.com.trier.bookstore.bookstore.repositories.GenreRepository;
import br.com.trier.bookstore.bookstore.services.GenreService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class GenreServiceImpl implements GenreService{

	@Autowired
	GenreRepository repository;

	private void validDescription(Genre genre) {
		if(genre.getDescription() == null || genre.getDescription().trim().isEmpty()) {
			throw new IntegrityViolation("Descrição do gênero está vazio");
		}
		Genre find = findByDescritpionIgnoreCase(genre.getDescription());
		if(find != null && find.getId() != genre.getId()) {
			throw new IntegrityViolation(
					"O gênero já contém o nome %s".formatted(genre.getDescription()));
		}
	}
	
	@Override
	public List<Genre> findAll() {
		List<Genre> list = repository.findAll();
		if(list.isEmpty()){
			throw new ObjectNotFound("Nenhum gênero encontrado");
		}
		return list;
	}

	@Override
	public Genre findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s do gênero não encontrado"));
	}

	@Override
	public Genre insert(Genre genre) {
		validDescription(genre);
		return repository.save(genre);
	}

	@Override
	public Genre update(Genre genre) {
		findById(genre.getId());
		validDescription(genre);
		return repository.save(genre);
	}

	@Override
	public void delete(Integer id) {
		Genre genre = findById(id);
		repository.delete(genre);
	}

	@Override
	public Genre findByDescritpionIgnoreCase(String name) {
		Genre genre = repository.findByDescriptionIgnoreCase(name);
		if(genre == null) {
			throw new ObjectNotFound("Gênero: %s não encontrado".formatted(name));
		}
		return genre;
	}
}
