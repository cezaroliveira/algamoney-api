package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping(path = "pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa) {

		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		// Cria uma URI para o objeto que foi salvo
		String location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(pessoaSalva.getCodigo()).toUriString();

		// Retorna como localização a URI para acessar o objeto que foi salvo
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("Location", location);

		// Cria uma resposta com o código 201 e retorna o JSON do objeto que foi salvo no banco
		return new ResponseEntity<Pessoa>(pessoaSalva, headers, HttpStatus.CREATED);
	}

	@GetMapping(path = "/{codigo}")
	public Optional<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		return pessoaRepository.findById(codigo);
	}

}