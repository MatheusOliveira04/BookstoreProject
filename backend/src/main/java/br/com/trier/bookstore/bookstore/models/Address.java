package br.com.trier.bookstore.bookstore.models;

import br.com.trier.bookstore.bookstore.models.dto.AddressDTO;
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
@Entity(name = "endereco")
public class Address {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_endereco")
	private Integer id;
	
	@Column(name = "rua_endereco")
	private String street;
	
	@Column(name = "bairro_endereco")
	private String neighborhood;
	
	@ManyToOne
	@JoinColumn(name = "cidade", nullable = false)
	private City city;
	
	public Address (AddressDTO dto, City city) {
		this (dto.getId(), dto.getStreet(), dto.getNeighborhood(), city);
	}

	public AddressDTO toDTO() {
		return new AddressDTO (getId(), getStreet(), getNeighborhood(),
				getCity().getId(), getCity().getName(), getCity().getUf());
	}
}
