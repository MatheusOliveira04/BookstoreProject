package br.com.trier.bookstore.bookstore.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {

	private Integer id;
	private Integer salespersonId;
	private String salespersonName;
	private String salespersonCpf;
	private Integer clientId;
	private String clientName;
	private String clientCpf;
}
