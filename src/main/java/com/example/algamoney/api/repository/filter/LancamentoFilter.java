package com.example.algamoney.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoFilter {

	private String descricao;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataVencimentoInicio;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataVencimentoFim;

}