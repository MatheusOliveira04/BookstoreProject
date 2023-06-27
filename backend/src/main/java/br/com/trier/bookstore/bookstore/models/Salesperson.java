package br.com.trier.bookstore.bookstore.models;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AttributeOverride(name = "nome_vendedor", column = @Column(name = "name"))
@AttributeOverride(name = "idade_vendedor", column = @Column(name = "age"))
@AttributeOverride(name = "cpf_vendedor", column = @Column(name = "cpf"))
@AttributeOverride(name = "endereco", column = @Column(name = "address"))
@AttributeOverride(name = "telefone", column = @Column(name = "telephone"))
@Entity(name = "vendedor")
@EqualsAndHashCode(of = "id")
@Getter
public class Salesperson extends Person {
	
	@Setter
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vendedor")
    private Integer id;

}
