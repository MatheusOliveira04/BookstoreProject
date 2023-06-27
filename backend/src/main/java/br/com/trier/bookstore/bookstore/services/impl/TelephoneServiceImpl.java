package br.com.trier.bookstore.bookstore.services.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.bookstore.bookstore.models.Telephone;
import br.com.trier.bookstore.bookstore.repositories.TelephoneRepository;
import br.com.trier.bookstore.bookstore.services.TelephoneService;
import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import br.com.trier.bookstore.bookstore.services.exceptions.ObjectNotFound;

@Service
public class TelephoneServiceImpl implements TelephoneService{

	@Autowired
	TelephoneRepository repository;

	private void validNumber(Telephone telephone) {
		if(telephone.getNumber() == null || telephone.getNumber().trim().isEmpty()) {
			throw new ObjectNotFound("Número do telefone está vazio");
		}
		DecimalFormat df = new DecimalFormat("(00)00000-0000");
		if(telephone.getNumber() != df.format("(00)00000-0000")) {
			throw new IntegrityViolation(
					"Formato de número inválido, favor utilizar o formato: (00)00000-0000");
		}
	}
	
	@Override
	public List<Telephone> findAll() {
		List<Telephone> list = repository.findAll();
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum telefone encontrado");
		}
		return list;
	}

	@Override
	public Telephone findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s do telefone não encontrado"));
	}

	@Override
	public Telephone insert(Telephone telephone) {
		validNumber(telephone);
		return repository.save(telephone);
	}

	@Override
	public Telephone update(Telephone telephone) {
		findById(telephone.getId());
		validNumber(telephone);
		return repository.save(telephone);
	}

	@Override
	public void delete(Integer id) {
		Telephone telephone = findById(id);
		repository.delete(telephone);
	}	
}
