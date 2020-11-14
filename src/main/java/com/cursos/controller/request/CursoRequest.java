package com.cursos.controller.request;

import javax.validation.constraints.NotBlank;

;

public class CursoRequest {

	private Integer id;
	
	@NotBlank(message = "{curso.nome.obrigatorio}")
	private String nome;
	
	private Double preco;

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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double perco) {
		this.preco = perco;
	}
	
}
