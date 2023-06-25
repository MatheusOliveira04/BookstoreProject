package br.com.trier.bookstore.bookstore.models;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Entity(name = "cliente")
@AttributeOverride(name = "nome_cliente", column = @Column(name = "name"))
@AttributeOverride(name = "idade_cliente", column = @Column(name = "age"))
@AttributeOverride(name = "cpf_cliente", column = @Column(name = "cpf"))
@AttributeOverride(name = "endereco", column = @Column(name = "address"))
@AttributeOverride(name = "telefone", column = @Column(name = "telephone"))
@EqualsAndHashCode(of = "id")
public class Client extends Person {

	@Setter
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_client")
    private Integer id;

}
