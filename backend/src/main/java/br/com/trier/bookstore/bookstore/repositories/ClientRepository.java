package br.com.trier.bookstore.bookstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Telephone;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
	
	Client findByCpf(String cpf);
	List<Client> findByAddressOrderByName(Address address);
	List<Client> findByTelephoneOrderByName(Telephone telephone);

}
