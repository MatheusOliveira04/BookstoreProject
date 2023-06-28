package br.com.trier.bookstore.bookstore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.Telephone;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
	
	Client findByCpf(String cpf);
	Optional<Client> findByAddressOrderByName(Address address);
	Optional<Client> findByTelephoneOrderByName(Telephone telephone);

}
