package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.City;
import br.com.trier.bookstore.bookstore.repositories.AddressRepository;
import br.com.trier.bookstore.bookstore.services.AddressService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	AddressRepository repository;

	private void validCity(Address address) {
		if(address.getCity() == null) {
			throw new IntegrityViolation("No endereço, o campo cidade não pode ser vazio");
		}
	}
	
	@Override
	public List<Address> findAll() {
		List<Address> list = repository.findAll();
		if(list.isEmpty()){
			throw new ObjectNotFound("Nenhum endereço encontrado");
		}
		return list;
	}

	@Override
	public Address findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s do endereço não encontrado".formatted(id)));
	}

	@Override
	public Address insert(Address address) {
		validCity(address);
		return repository.save(address);
	}

	@Override
	public Address update(Address address) {
		findById(address.getId());
		validCity(address);
		return repository.save(address);
	}

	@Override
	public void delete(Integer id) {
		Address address = findById(id);
		repository.delete(address);
	}

	@Override
	public List<Address> findByCity(City city) {
		List<Address> address = repository.findByCity(city);
		if(address.isEmpty()) {
			throw new ObjectNotFound(
					"A cidade: %s do endereço não foi encontrada".formatted(city.getName()));
		}
		return address;
	}

}
