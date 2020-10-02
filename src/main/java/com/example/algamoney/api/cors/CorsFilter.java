package com.example.algamoney.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.algamoney.api.config.property.AlgamoneyApiProperty;

/**
 * Filtro responsável por permitir o CORS.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		httpServletResponse.setHeader("Access-Control-Allow-Origin", algamoneyApiProperty.getSecurity().getCors().getAllowOrigin());
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

		if ("OPTIONS".equals(httpServletRequest.getMethod())) {
			httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
			httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
			httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Authorization, Content-Type, Accept");
			httpServletResponse.setHeader("Access-Control-Expose-Headers", "Location");

			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}

		else {
			chain.doFilter(request, response);
		}
	}

}
