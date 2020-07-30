package com.example.algamoney.api.service;

import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService extends AbstractService<Pessoa, PessoaRepository> {

	public Pessoa atualizarAtivo(Long codigo, Boolean ativo) {

		Pessoa pessoaNaBase = obter(codigo);

		pessoaNaBase.setAtivo(ativo);

		return entityRepository.save(pessoaNaBase);
	}

}