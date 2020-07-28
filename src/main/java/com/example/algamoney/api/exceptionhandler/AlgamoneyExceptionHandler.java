package com.example.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.algamoney.api.consts.MessageConsts;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = messageSource.getMessage(MessageConsts.MENSAGEM_INVALIDA, null, LocaleContextHolder.getLocale());

		String mensagemDesenvolvedor = ex.getCause().toString();

		return handleExceptionInternal(ex, Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor)), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, listErros(ex.getBindingResult()), headers, status, request);
	}

	@ExceptionHandler({EmptyResultDataAccessException.class})
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	protected void handleEmptyResultDataAccessException() {

	}

	private List<Erro> listErros(BindingResult bindingResult) {

		List<Erro> listErros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {

			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

			listErros.add(new Erro(mensagemUsuario, fieldError.toString()));
		}

		return listErros;
	}

}