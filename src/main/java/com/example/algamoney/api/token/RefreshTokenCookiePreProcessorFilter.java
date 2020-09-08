package com.example.algamoney.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Filtro responsável por adicionar o refresh_token como cookie na requisição.
 * 
 * Essa estratégia garante maior segurança, visto que o refresh_token não pode
 * ser obtido pelo Javasript, estando disponível apenas para o HTTP.
 * 
 * Desta forma também não é mais necessário adicionar manualmente o
 * refresh_token retornado para cada nova requisição que será realizada.
 * 
 * @see RefreshTokenPostProcessor
 */
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		// Verifica se é uma requisição de autenticação do tipo refresh_token
		if ("/oauth/token".equalsIgnoreCase(httpServletRequest.getRequestURI())
				&& "refresh_token".equals(httpServletRequest.getParameter("grant_type"))
				&& httpServletRequest.getCookies() != null) {

			for (Cookie cookie : httpServletRequest.getCookies()) {

				// Cria um novo request contendo o refresh_token como parâmetro
				if (cookie.getName().equals("refreshToken")) {
					httpServletRequest = new MyServletRequestWrapper(httpServletRequest, cookie.getValue());
				}
			}
		}

		chain.doFilter(httpServletRequest, response);
	}

	/**
	 * Classe criada unicamente para permitir a inclusão do refresh_token nos
	 * parâmetros da requisição, visto que o método
	 * {@link ServletRequest#getParameterMap()} retorna uma instância imutável.
	 */
	static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private String refreshToken;

		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}

		/**
		 * Retorna o mesmo {@link ServletRequest#getParameterMap()}, apenas adicionando
		 * o refresh_token na requisição.
		 */
		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> parameterMap = new ParameterMap<>(getRequest().getParameterMap());
			parameterMap.put("refresh_token", new String[] { this.refreshToken });
			parameterMap.setLocked(true);
			return parameterMap;
		}

	}

}
