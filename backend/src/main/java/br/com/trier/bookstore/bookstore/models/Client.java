package br.com.trier.bookstore.bookstore.models;

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

@Entity(name = "cliente")
@AttributeOverride(name = "name", column = @Column(name = "nome_cliente"))
@AttributeOverride(name = "cpf", column = @Column(name = "cpf_cliente"))
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Client extends Person {

	@Setter
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
    private Integer id;

	public Client(Integer id, String name, String cpf, Address address, Telephone telephone) {
		super(name, cpf, address, telephone);
		this.id = id;
	}
}
