package com.example.algamoney.api.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.algamoney.api.consts.MessageConsts;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ExceptionUtils exceptionUtils;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, exceptionUtils.listErros(ex, MessageConsts.MENSAGEM_INVALIDA), 
				headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, exceptionUtils.listErros(ex.getBindingResult()), headers, status, request);
	}

	@ExceptionHandler({EmptyResultDataAccessException.class})
	//	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	protected ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {

		return handleExceptionInternal(ex, exceptionUtils.listErros(ex, MessageConsts.RECURSO_NAO_ENCONTRADO),
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({DataIntegrityViolationException.class})
	protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {

		return handleExceptionInternal(ex, exceptionUtils.listErros(ex, MessageConsts.RECURSO_OPERACAO_NAO_PERMITIDA),
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

}