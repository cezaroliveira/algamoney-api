package com.example.algamoney.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.categoria.CategoriaRepository;
import com.example.algamoney.api.service.CategoriaService;

@RestController
@RequestMapping(path = "/categorias")
public class CategoriaResource extends AbstractCrudResource<Categoria, CategoriaRepository, CategoriaService> {

	@Override
	@PreAuthorize(value = "hasRole('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Page<Categoria>> filtrar(
			@RequestParam(value = "$search", required = false) String search,
			@RequestParam(value = "$filter", required = false) String filter,
			Pageable pageable) {
		return super.filtrar(search, filter, pageable);
	}

	@Override
	@PreAuthorize(value = "hasRole('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria entity, HttpServletResponse response) {
		return super.criar(entity, response);
	}

}