package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.City;
import br.com.trier.bookstore.bookstore.repositories.CityRepository;
import br.com.trier.bookstore.bookstore.services.CityService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	CityRepository repository;

	private void validName(City city) {
		if(city.getName() == null || city.getName().trim().isEmpty()) {
			throw new IntegrityViolation("Nome da cidade está vazio");
		}
		City c = repository.findByNameIgnoreCase(city.getName());
		if(c != null && c.getId() != city.getId()) {
			throw new IntegrityViolation("A cidade já contém o nome %s".formatted(city.getName()));
		}
	}
	
	private void validUf(City city) {
		if(city.getUf().trim().length() != 2 || city.getUf() == null) {
			throw new IntegrityViolation("Uf da cidade deve conter 2 caracteres");
		}
	}
	
	
	@Override
	public List<City> findAll() {
		List<City> list = repository.findAll();
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhuma cidade encontrada");
		}
		return list;
	}

	@Override
	public City findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s da cidade não encontrado".formatted(id)));
	}

	@Override
	public City insert(City city) {
		validName(city);
		validUf(city);
		return repository.save(city);
	}

	@Override
	public City update(City city){
		findById(city.getId());
		validName(city);
		validUf(city);
		return repository.save(city);
	}

	@Override
	public void delete(Integer id) {
		City city = findById(id);
		repository.delete(city);
	}

	@Override
	public City findByNameIgnoreCase(String name) {
		City city = repository.findByNameIgnoreCase(name);
		if(city == null) {
			throw new ObjectNotFound("nome: %s não encontrado na cidade".formatted(name));
		}
		return city;
	}

}
