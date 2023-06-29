package br.com.trier.bookstore.bookstore.models;

import br.com.trier.bookstore.bookstore.models.dto.PersonDTO;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AttributeOverride(name = "name", column = @Column(name = "nome_vendedor"))
@AttributeOverride(name = "cpf", column = @Column(name = "cpf_vendedor"))
@Entity(name = "vendedor")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Salesperson extends Person {
	
	@Setter
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vendedor")
    private Integer id;

	public Salesperson(Integer id, String name, String cpf, Address address, Telephone telephone) {
		super(name, cpf, address, telephone);
		this.id = id;
	}

	public Salesperson(PersonDTO dto, Address address, Telephone telephone) {
		this(dto.getId(), dto.getName(), dto.getCpf(), address, telephone);
	}
	
	public PersonDTO toDTO() {
		return new PersonDTO(getId(), getName(), getCpf(),
				getAddress().toDTO(), getTelephone().getId(), getTelephone().getNumber());
	}
}
