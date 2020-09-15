package com.example.algamoney.api.repository.lancamento;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LancamentoFilter {

	private String descricao;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataVencimentoInicio;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataVencimentoFim;

}
