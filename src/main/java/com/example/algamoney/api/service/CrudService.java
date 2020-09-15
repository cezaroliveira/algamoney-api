package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.BaseEntity;
import com.example.algamoney.api.repository.CopyProperties;

/**
 * Interface genérica para CRUD na camada de negócio.
 * 
 * @param <EntityType>       entidade JPA
 * @param <EntityRepository> Repository da entidade
 */
public interface CrudService<EntityType extends BaseEntity, EntityRepository extends JpaRepository<EntityType, Long> & CopyProperties> {

	EntityType criar(EntityType EntityType);

	EntityType atualizar(Long id, EntityType EntityType);

	void excluir(Long id);

	EntityType obter(Long id);

	List<EntityType> listar();

    Page<EntityType> filtrar(String search, String filter, Pageable pageable);

}
