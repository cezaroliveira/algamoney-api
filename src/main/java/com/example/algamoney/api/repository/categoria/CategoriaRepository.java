package com.example.algamoney.api.repository.categoria;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CopyProperties;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaRepositoryQuery, CopyProperties {

}
