package com.example.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public final class ExceptionUtils {

	@Autowired
	private MessageSource messageSource;

	public List<Erro> listErros(Exception ex, String codigoMensagem) {
		return listErros(ex, codigoMensagem, null);
	}

	public List<Erro> listErros(Exception ex, String codigoMensagem, Object[] argumentosMensagem) {

		String mensagemUsuario = messageSource.getMessage(codigoMensagem, argumentosMensagem, LocaleContextHolder.getLocale());

		String mensagemDesenvolvedor = org.apache.commons.lang3.exception.ExceptionUtils.getRootCause(ex).toString();

		return Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
	}

	public List<Erro> listErros(BindingResult bindingResult) {

		List<Erro> listErros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {

			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

			listErros.add(new Erro(mensagemUsuario, fieldError.toString()));
		}

		return listErros;
	}

}