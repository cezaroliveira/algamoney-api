package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService extends AbstractService<Pessoa, PessoaRepository> {

	public Pessoa atualizarAtivo(Long codigo, Boolean ativo) {

		Optional<Pessoa> pessoaNaBase = obter(codigo);

		pessoaNaBase.get().setAtivo(ativo);

		return entityRepository.save(pessoaNaBase.get());
	}

}