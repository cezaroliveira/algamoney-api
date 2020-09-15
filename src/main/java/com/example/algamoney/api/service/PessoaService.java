package com.example.algamoney.api.service;

import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.pessoa.PessoaRepository;

@Service
public class PessoaService extends AbstractCrudService<Pessoa, PessoaRepository> {

	public Pessoa atualizarAtivo(Long codigo, Boolean ativo) {

		Pessoa pessoaNaBase = obter(codigo);

		pessoaNaBase.setAtivo(ativo);

		return entityRepository.save(pessoaNaBase);
	}

}