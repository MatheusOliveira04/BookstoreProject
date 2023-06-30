package br.com.trier.bookstore.bookstore.models.dto;

import java.util.List;

import br.com.trier.bookstore.bookstore.models.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookSoldBySalespersonDTO {

	private String name;
	private Integer quantityBook;
	private List<Book> book;
}
