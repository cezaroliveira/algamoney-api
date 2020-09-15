package com.example.algamoney.api.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.categoria.CategoriaRepository;
import com.example.algamoney.api.service.CategoriaService;

@RestController
@RequestMapping(path = "/categorias")
public class CategoriaResource extends AbstractCrudResource<Categoria, CategoriaRepository, CategoriaService> {

}