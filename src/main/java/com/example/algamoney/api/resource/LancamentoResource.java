package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.algamoney.api.consts.MessageConsts;
import com.example.algamoney.api.exceptionhandler.Erro;
import com.example.algamoney.api.exceptionhandler.ExceptionUtils;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.service.LancamentoService;
import com.example.algamoney.api.service.exception.CategoriaInexistenteException;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping(path = "/lancamentos")
public class LancamentoResource extends AbstractResource<Lancamento, LancamentoRepository, LancamentoService> {

	@Autowired
	private ExceptionUtils exceptionUtils;

	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	protected ResponseEntity<List<Erro>> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex, WebRequest request) {
		return ResponseEntity.badRequest().body(exceptionUtils.listErros(ex, MessageConsts.PESSOA_INEXISTENTE_OU_INATIVA));
	}

	@ExceptionHandler({CategoriaInexistenteException.class})
	protected ResponseEntity<List<Erro>> handleCategoriaInexistenteException(CategoriaInexistenteException ex, WebRequest request) {
		return ResponseEntity.badRequest().body(exceptionUtils.listErros(ex, MessageConsts.CATEGORIA_INEXISTENTE));
	}

}