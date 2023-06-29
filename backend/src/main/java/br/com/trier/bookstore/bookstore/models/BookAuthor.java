package br.com.trier.bookstore.bookstore.models;

import br.com.trier.bookstore.bookstore.models.dto.BookAuthorDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "livro_autor")
public class BookAuthor {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_livro_autor")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "livro", nullable = false)
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "autor")
	private Author author;
	
	public BookAuthor(BookAuthorDTO dto, Book book, Author author) {
		this(dto.getId(), book, author);
	}
	
	public BookAuthorDTO toDTO() {
		return new BookAuthorDTO(getId(), getBook().getId(), getBook().getName(),
				getAuthor().getId(), getAuthor().getName());
	}
}
