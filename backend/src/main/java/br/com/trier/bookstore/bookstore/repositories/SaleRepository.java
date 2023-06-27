package br.com.trier.bookstore.bookstore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Client;
import br.com.trier.bookstore.bookstore.models.Sale;
import br.com.trier.bookstore.bookstore.models.Salesperson;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer>{

	Optional<Sale> findBySalesperson(Salesperson salesperson);
	Optional<Sale> findByClient(Client client);
}
