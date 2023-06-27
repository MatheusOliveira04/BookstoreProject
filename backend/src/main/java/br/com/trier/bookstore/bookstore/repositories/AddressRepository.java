package br.com.trier.bookstore.bookstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Address;
import br.com.trier.bookstore.bookstore.models.City;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

	List<Address> findByCity(City city);
}
