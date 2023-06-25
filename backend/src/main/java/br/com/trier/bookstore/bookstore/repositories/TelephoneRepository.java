package br.com.trier.bookstore.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.bookstore.bookstore.models.Telephone;

@Repository
public interface TelephoneRepository extends JpaRepository<Telephone, Integer>{

}
