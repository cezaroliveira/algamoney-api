package com.example.algamoney.api.event.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {

		// Cria uma URI para o objeto que foi salvo
		String location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(event.getCodigo()).toUriString();

		// Retorna como localização a URI para acessar o objeto que foi salvo
		event.getResponse().setHeader(HttpHeaders.LOCATION, location);
	}

}