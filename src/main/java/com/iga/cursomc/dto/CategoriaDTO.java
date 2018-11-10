package com.iga.cursomc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.iga.cursomc.domain.Categoria;
import com.iga.cursomc.domain.Produto;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max= 80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;

	private List<Produto> produtos = new ArrayList<Produto>();
	
	public CategoriaDTO() {	
	}
	
	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
		produtos = obj.getProdutos().stream().map(x -> new Produto(x.getId(), x.getNome(), x.getPreco())).collect(Collectors.toList());
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
	
}
