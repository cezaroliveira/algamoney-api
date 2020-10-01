package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.algamoney.api.consts.MessageConsts;
import com.example.algamoney.api.exceptionhandler.Erro;
import com.example.algamoney.api.exceptionhandler.ExceptionUtils;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.lancamento.LancamentoFilter;
import com.example.algamoney.api.repository.lancamento.LancamentoRepository;
import com.example.algamoney.api.service.LancamentoService;
import com.example.algamoney.api.service.exception.CategoriaInexistenteException;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping(path = "/lancamentos")
public class LancamentoResource extends AbstractCrudResource<Lancamento, LancamentoRepository, LancamentoService> {

	@Autowired
	private ExceptionUtils exceptionUtils;

	@GetMapping(path = "/filtrarEspecifico")
	public Page<Lancamento> listar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return entityRepository.filtrar(lancamentoFilter, pageable);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<Lancamento> listar() {
		return super.listar();
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Page<Lancamento>> filtrar(
			@RequestParam(value = "$search", required = false) String search,
			@RequestParam(value = "$filter", required = false) String filter,
			Pageable pageable) {
		return super.filtrar(search, filter, pageable);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento entity, HttpServletResponse response) {
		return super.criar(entity, response);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		return super.buscarPeloCodigo(codigo);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		super.remover(codigo);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento entity) {
		return super.atualizar(codigo, entity);
	}

	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	protected ResponseEntity<List<Erro>> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex, WebRequest request) {
		return ResponseEntity.badRequest().body(exceptionUtils.listErros(ex, MessageConsts.PESSOA_INEXISTENTE_OU_INATIVA));
	}

	@ExceptionHandler({CategoriaInexistenteException.class})
	protected ResponseEntity<List<Erro>> handleCategoriaInexistenteException(CategoriaInexistenteException ex, WebRequest request) {
		return ResponseEntity.badRequest().body(exceptionUtils.listErros(ex, MessageConsts.CATEGORIA_INEXISTENTE));
	}

}