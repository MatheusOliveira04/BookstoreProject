package br.com.trier.bookstore.bookstore.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	private String email;
	private String password;
}
