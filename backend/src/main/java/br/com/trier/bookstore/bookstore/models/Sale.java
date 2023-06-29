package br.com.trier.bookstore.bookstore.models;

import br.com.trier.bookstore.bookstore.models.dto.SaleDTO;
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
@Entity(name = "venda")
public class Sale {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_venda")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cliente", nullable = false)
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "vendedor")
	private Salesperson salesperson;
	
	public Sale(SaleDTO dto, Client client, Salesperson salesperson) {
		this(dto.getId(), client, salesperson);
	}
	
	public SaleDTO toDTO() {
		return new SaleDTO(getId(), getSalesperson().getId() ,getSalesperson().getName(),
				getSalesperson().getCpf(), getClient().getId(), getClient().getName(),
				getClient().getCpf());
	}
}
