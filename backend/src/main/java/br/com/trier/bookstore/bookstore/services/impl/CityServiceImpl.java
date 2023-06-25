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
		if(city.getName() == null) {
			throw new IntegrityViolation("Nome da cidade está vazio");
		}
		City c = findByNameIgnoreCase(city.getName());
		if(c != null && c.getId() != c.getId()) {
			throw new IntegrityViolation("A cidade já contém o nome %s".formatted(city.getName()));
		}
	}
	
	private void validUf(City city) {
		if(city.getUf().length() != 2) {
			throw new IntegrityViolation("Uf da cidade deve conter 2 caracteres");
		}
		if(city.getUf().equals(null)) {
			throw new IntegrityViolation("Uf da cidade está vazio, deve conter 2 caracteres");
		}
	}
	
	
	@Override
	public List<City> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City insert(City city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City update(City city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public City findByNameIgnoreCase(String name) {
		City city = repository.findByNameIgnoreCase(name);
		return city;
	}

}
