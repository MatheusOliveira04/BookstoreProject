package br.com.trier.bookstore.bookstore.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.Telephone;
import br.com.trier.bookstore.bookstore.repositories.SalespersonRepository;
import br.com.trier.bookstore.bookstore.services.SalespersonService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class SalespersonServiceImpl implements SalespersonService {

	@Autowired
	SalespersonRepository repository;

	private void validName(Salesperson salesperson) {
		if (salesperson.getName() == null || salesperson.getName().trim().isEmpty()) {
			throw new IntegrityViolation("Nome do vendedor está vazio");
		}
	}

	private void validCpf(Salesperson salesperson) {
		if (salesperson.getCpf() == null) {
			throw new IntegrityViolation("Cpf do vendedor está vazio");
		}
		String cpfFormat = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$";
		if (!salesperson.getCpf().matches(cpfFormat)) {
			throw new IntegrityViolation("Formato do cpf inválido, favor utilizar o formato: 000.000.000-00");
		}
		Salesperson find = repository.findByCpf(salesperson.getCpf());
		if (find != null && find.getId() != salesperson.getId()) {
			throw new IntegrityViolation(
					"O vendedor já contém o cpf %s".formatted(salesperson.getCpf()));
		}
	}

	@Override
	public List<Salesperson> findAll() {
		List<Salesperson> list = repository.findAll();
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum vendedor encontrado");
		}
		return list;
	}

	@Override
	public Salesperson findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Id: %s do vendedor não encontrado".formatted(id)));
	}

	@Override
	public Salesperson insert(Salesperson salesperson) {
		validName(salesperson);
		validCpf(salesperson);
		return repository.save(salesperson);
	}

	@Override
	public Salesperson update(Salesperson salesperson) {
		findById(salesperson.getId());
		validName(salesperson);
		validCpf(salesperson);
		return repository.save(salesperson);
	}

	@Override
	public void delete(Integer id) {
		Salesperson salesperson = findById(id);
		repository.delete(salesperson);
	}

	@Override
	public List<Salesperson> findByAddressOrderByName(Address address) {
		List<Salesperson> salesperson = repository.findByAddressOrderByName(address);
		if (salesperson.isEmpty()) {
			throw new ObjectNotFound("O endereço: %s do vendedor não foi encontrado".formatted(address.getId()));
		}
		return salesperson;
	}

	@Override
	public List<Salesperson> findByTelephoneOrderByName(Telephone telephone) {
		List<Salesperson> salesperson = repository.findByTelephoneOrderByName(telephone);
		if (salesperson.isEmpty()) {
			throw new ObjectNotFound("O Telefone: %s do vendedor não foi encontrado".formatted(telephone.getId()));
		}
		return salesperson;
	}

	@Override
	public Salesperson findByCpf(String cpf) {
		Salesperson salesperson = repository.findByCpf(cpf);
		if (salesperson == null) {
			throw new ObjectNotFound("cpf: %s não encontrado no vendedor".formatted(cpf));
		}
		return salesperson;
	}
}
