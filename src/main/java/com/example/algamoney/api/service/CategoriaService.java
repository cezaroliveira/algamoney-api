package com.example.algamoney.api.service;

import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.categoria.CategoriaRepository;

@Service
public class CategoriaService extends AbstractCrudService<Categoria, CategoriaRepository> {

}