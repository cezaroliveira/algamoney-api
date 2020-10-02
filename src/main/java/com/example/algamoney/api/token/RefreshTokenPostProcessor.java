package com.example.algamoney.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.algamoney.api.config.property.AlgamoneyApiProperty;

/**
 * Classe responsável por adicionar o refresh_token a um cookie que pode ser
 * acessado apenas pelo HTTP.
 * 
 * Essa estratégia garante maior segurança, visto que o refresh_token não pode
 * ser obtido pelo Javascript, estando disponível apenas para o HTTP.
 * 
 * @see RefreshTokenCookiePreProcessorFilter
 */
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
					ServerHttpRequest request, ServerHttpResponse response) {

		HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse httpServletResponse = ((ServletServerHttpResponse) response).getServletResponse();

		adicionarRefreshTokenNoCookie(body.getRefreshToken().getValue(), httpServletRequest, httpServletResponse);

		removerRefreshTokenDoBody((DefaultOAuth2AccessToken) body);

		return body;
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		Cookie cookie = new Cookie("refreshToken", refreshToken);
		cookie.setHttpOnly(true);
		cookie.setSecure(algamoneyApiProperty.getSecurity().isEnableHttps());
		cookie.setPath(httpServletRequest.getContextPath() + "/oauth/token");
		cookie.setMaxAge(2592000);

		httpServletResponse.addCookie(cookie);
	}

	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken body) {
		body.setRefreshToken(null);
	}

}
