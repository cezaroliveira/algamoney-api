package com.example.algamoney.api.config.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

	private String environment;

	private Security security;

	@Getter
	@Setter
	public static class Security {
		private boolean enableHttps;
		@Autowired
		private Cors cors;
	}

	@Getter
	@Setter
	public static class Cors {
		private String allowOrigin;
	}

}
