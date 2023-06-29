package br.com.trier.bookstore.bookstore.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	private Integer id;
	private Integer quantityBook;
	private Integer bookId;
	private String bookName;
	private SaleDTO sale;
}
