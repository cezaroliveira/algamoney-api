package com.example.algamoney.api.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EndpointListener implements ApplicationListener<ApplicationEvent> {

	@Autowired
	private RequestMappingHandlerMapping requestHandlerMapping;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			requestHandlerMapping.getHandlerMethods()
			.forEach((key, value) ->  log.info("Mapped endpoint {} = {}", key, value));
		}
	}

}