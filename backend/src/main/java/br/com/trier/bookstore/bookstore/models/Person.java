package br.com.trier.bookstore.bookstore.models;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Person {
	
	private String name;
	
	private Integer age;
	
	@Column(columnDefinition = "VARCHAR(14) CHECK (cpf ~ '^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$')")
	private String cpf;
	
	@JoinColumn(name = "endereco")
	@ManyToOne
	private Address address;
	
	@JoinColumn(name = "telefone")
	@ManyToOne
	private Telephone telephone;
}
