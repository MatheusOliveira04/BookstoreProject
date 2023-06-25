package br.com.trier.bookstore.bookstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.Salesperson;
import br.com.trier.bookstore.bookstore.models.Telephone;

@Repository
public interface SalespersonRepository extends JpaRepository<Salesperson, Integer>{
	
	List<Salesperson> findByAddressOrderByName(Address address);
	List<Salesperson> findByTelephoneOrderByName(Telephone telephone);
 
}
