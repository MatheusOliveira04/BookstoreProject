package br.com.trier.bookstore.bookstore.models;

import br.com.trier.bookstore.bookstore.models.dto.GenreBookDTO;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "genero_livro")
public class GenreBook {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_genero_livro")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "livro", nullable = false)
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "genero", nullable = false)
	private Genre genre;
	
	public GenreBook(GenreBookDTO dto, Book book , Genre genre) {
		this(dto.getId(), book, genre);
	}
	
	public GenreBookDTO toDTO() {
		return new GenreBookDTO(getId(), getBook().getId(), getBook().getName(),
				getGenre().getId(), getGenre().getDescription());
	}
}
