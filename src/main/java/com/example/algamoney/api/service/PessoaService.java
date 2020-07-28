package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {

		Optional<Pessoa> pessoaNaBase = obter(codigo);

		BeanUtils.copyProperties(pessoa, pessoaNaBase.get(), "codigo");

		return pessoaRepository.save(pessoaNaBase.get());
	}

	public Pessoa atualizarAtivo(Long codigo, Boolean ativo) {

		Optional<Pessoa> pessoaNaBase = obter(codigo);

		pessoaNaBase.get().setAtivo(ativo);

		return pessoaRepository.save(pessoaNaBase.get());
	}

	private Optional<Pessoa> obter(Long codigo) {

		Optional<Pessoa> pessoaNaBase = pessoaRepository.findById(codigo);

		if (!pessoaNaBase.isPresent()) {
			throw new EmptyResultDataAccessException(/* "Pessoa não existe!", */1);
		}

		return pessoaNaBase;
	}

}