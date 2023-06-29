package br.com.trier.bookstore.bookstore.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

	private Integer id;
	private String name;
	private String cpf;
	private AddressDTO addressDTO;
	private Integer telephoneId;
	private String telephoneNumber;
}
