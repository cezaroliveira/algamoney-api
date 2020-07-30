package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.CategoriaRepository;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.CategoriaInexistenteException;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService extends AbstractService<Lancamento, LancamentoRepository> {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public Lancamento criar(Lancamento entity) {

		Optional<Pessoa> pessoaNaBase = pessoaRepository.findById(entity.getPessoa().getCodigo());

		if (!pessoaNaBase.isPresent() || !pessoaNaBase.get().getAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}

		Optional<Categoria> categoriaNaBase = categoriaRepository.findById(entity.getCategoria().getCodigo());

		if (!categoriaNaBase.isPresent()) {
			throw new CategoriaInexistenteException();
		}

		return super.criar(entity);
	}

}