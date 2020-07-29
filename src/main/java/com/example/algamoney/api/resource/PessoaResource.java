package com.example.algamoney.api.resource;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping(path = "pessoas")
public class PessoaResource extends AbstractResource<Pessoa, PessoaRepository, PessoaService> {

	public ResponseEntity<Pessoa> atualizarAtivo(@PathVariable Long codigo, @Valid @RequestBody Boolean ativo) {

		Pessoa pessoaNaBase = entityService.atualizarAtivo(codigo, ativo);

		return ResponseEntity.ok(pessoaNaBase);
	}

}