package br.com.trier.bookstore.bookstore.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientOfSalesperson {

	private String salesperson;
	private Integer quantitySale;
	private List<PersonDTO> client;
}
