package com.iga.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.iga.cursomc.domain.Cliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	@NotEmpty(message="Prenchimento obrigatório")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;

	@NotEmpty(message="Prenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;

	public ClienteDTO() {
	}

	public ClienteDTO(Cliente obj) {
		// Instanciando o DTO
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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