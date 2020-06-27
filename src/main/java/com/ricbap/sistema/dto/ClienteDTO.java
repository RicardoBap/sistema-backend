package com.ricbap.sistema.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.ricbap.sistema.domain.Cliente;
import com.ricbap.sistema.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "Preencimento obrigatório")
	@Length(min = 4, max = 40, message = "O nome deve estar entre 4 e 40 caracteres")
	private String nome;
	
	@NotEmpty(message = "Preencimento obrigatório")
	@Email(message = "E-mail inválido")
	private String email;
	
	public ClienteDTO() {}

	public ClienteDTO(Cliente cliente) {
		id = cliente.getId();
		nome = cliente.getNome();
		email = cliente.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
