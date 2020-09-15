package com.example.algamoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.FilterQuery;

public interface LancamentoRepositoryQuery extends FilterQuery<Lancamento> {

	Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

}
