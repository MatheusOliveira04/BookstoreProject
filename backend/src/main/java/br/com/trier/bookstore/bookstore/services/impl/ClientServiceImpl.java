package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.Telephone;
import br.com.trier.bookstore.bookstore.repositories.ClientRepository;
import br.com.trier.bookstore.bookstore.services.ClientService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	ClientRepository repository;

	private void validName(Client client) {
		if(client.getName() == null || client.getName().trim().isEmpty()) {
			throw new IntegrityViolation("Nome do cliente está vazio");
		}
	}
	
	private void validCpf(Client client) {
		if(client.getCpf() == null) {
			throw new IntegrityViolation("Cpf do cliente está vazio");
		}
		 String cpfFormat = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$";
		 if(cpfFormat.matches(client.getCpf())) {
			 throw new IntegrityViolation(
					 "Formato do cpf inválido, favor utilizar o formato: 000.000.000-00");
		 }
		 Client find = repository.findByCpf(client.getCpf());
			if(find != null && find.getId() != client.getId()) {
				throw new IntegrityViolation("O cliente já contém o cpf %s".formatted(client.getCpf()));
			}
	}

	@Override
	public List<Client> findAll() {
			List<Client> list = repository.findAll();
			if(list.isEmpty()) {
				throw new ObjectNotFound("Nenhum cliente encontrado");
			}
			return list;
	}

	@Override
	public Client findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s do cliente não encontrado".formatted(id)));
	}

	@Override
	public Client insert(Client client) {
		validName(client);
		validCpf(client);
		return repository.save(client);
	}

	@Override
	public Client update(Client client) {
		findById(client.getId());
		validName(client);
		validCpf(client);
		return repository.save(client);
	}

	@Override
	public void delete(Integer id) {
		Client client = findById(id);
		repository.delete(client);
		
	}

	@Override
	public Optional<Client> findByAddressOrderByName(Address address) {
		Optional<Client> client = repository.findByAddressOrderByName(address);
		if(client.isEmpty()) {
			throw new ObjectNotFound(
					"O endereço: %s do cliente não foi encontrado".formatted(address.getId()));
		}
		return client;
	}

	@Override
	public Optional<Client> findByTelephoneOrderByName(Telephone telephone) {
		Optional<Client> client = repository.findByTelephoneOrderByName(telephone);
		if(client.isEmpty()) {
			throw new ObjectNotFound("O telefone: %s do cliente não foi encontrado");
		}
		return client;
	}

	@Override
	public Client findByCpf(String cpf) {
		Client client = repository.findByCpf(cpf);
		if(client == null) {
			throw new ObjectNotFound("cpf: %s não encontrado no cliente".formatted(cpf));
		}
		return client;
	}

	
}
