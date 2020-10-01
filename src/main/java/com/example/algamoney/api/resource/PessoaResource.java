package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.pessoa.PessoaRepository;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping(path = "pessoas")
public class PessoaResource extends AbstractCrudResource<Pessoa, PessoaRepository, PessoaService> {

	public ResponseEntity<Pessoa> atualizarAtivo(@PathVariable Long codigo, @Valid @RequestBody Boolean ativo) {

		Pessoa pessoaNaBase = entityService.atualizarAtivo(codigo, ativo);

		return ResponseEntity.ok(pessoaNaBase);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public List<Pessoa> listar() {
		return super.listar();
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Page<Pessoa>> filtrar(
			@RequestParam(value = "$search", required = false) String search,
			@RequestParam(value = "$filter", required = false) String filter,
			Pageable pageable) {
		return super.filtrar(search, filter, pageable);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa entity, HttpServletResponse response) {
		return super.criar(entity, response);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		return super.buscarPeloCodigo(codigo);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		super.remover(codigo);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa entity) {
		return super.atualizar(codigo, entity);
	}

}