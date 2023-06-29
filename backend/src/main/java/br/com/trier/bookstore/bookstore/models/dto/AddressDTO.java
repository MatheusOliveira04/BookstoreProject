package br.com.trier.bookstore.bookstore.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

	private Integer id;
	private String street;
	private String neighborhood;
	private Integer cityId;
	private String cityName;
	private String cityUf;
}
