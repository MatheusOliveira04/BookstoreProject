package br.com.trier.bookstore.bookstore.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorDTO {

	private Integer id;
	private Integer bookId;
	private String bookName;
	private Integer authorId;
	private String authorName;
}
