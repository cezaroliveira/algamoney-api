package com.example.algamoney.api.repository.query;

import java.util.List;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);

}