package com.example.algamoney.api.repository.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.CopyProperties;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery, CopyProperties {

}
