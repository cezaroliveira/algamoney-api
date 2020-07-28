package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping(path = "pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		// Publica o evento responsável por gerar no header a localização para o objeto que foi salvo
		eventPublisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

		// Cria uma resposta com o código 201 e retorna o JSON do objeto que foi salvo no banco
		return new ResponseEntity<Pessoa>(pessoaSalva, HttpStatus.CREATED);
	}

	@GetMapping(path = "/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {

		Optional<Pessoa> pessoaEncontrada = pessoaRepository.findById(codigo);

		return pessoaEncontrada.isPresent() ? ResponseEntity.ok(pessoaEncontrada.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping(path = "/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		pessoaRepository.deleteById(codigo);
	}

	@PutMapping(path = "/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {

		Pessoa pessoaNaBase = pessoaService.atualizar(codigo, pessoa);

		return ResponseEntity.ok(pessoaNaBase);
	}

	@PutMapping(path = "/{codigo}/ativo")
	public ResponseEntity<Pessoa> atualizarAtivo(@PathVariable Long codigo, @Valid @RequestBody Boolean ativo) {

		Pessoa pessoaNaBase = pessoaService.atualizarAtivo(codigo, ativo);

		return ResponseEntity.ok(pessoaNaBase);
	}

}