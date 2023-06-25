package br.com.trier.bookstore.bookstore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "pedido")
public class Order {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private Integer id;
	
	@Column(name = "quantidade_livro", nullable = false)
	private Integer quantityBook;
	
	@ManyToOne
	@Column(name = "livro", nullable = false)
	private Book book;
	
	@ManyToOne
	@Column(name = "venda", nullable = false )
	private Sale sale;
}
