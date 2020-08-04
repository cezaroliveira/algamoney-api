package com.example.algamoney.api.model;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class Endereco {

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private String cep;

	private String cidade;

	private String estado;

}