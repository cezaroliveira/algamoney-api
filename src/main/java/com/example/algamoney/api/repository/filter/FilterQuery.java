package com.example.algamoney.api.repository.filter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.BaseEntity;

/**
 * Interface genérica para filtrar, paginar e ordenar.
 *
 * @param <EntityType> entidade JPA
 */
public interface FilterQuery<EntityType extends BaseEntity> {

	/**
	 * Centraliza a lógica genérica de filtro.
	 * 
	 * @param search   representa a busca rápida que pode ser aplicada a vários
	 *                 campos
	 * @param filter   contém todos os campos e seus respectivos
	 * @param pageable contém os parâmetros de paginação
	 * @return {@link Page} contendo a lista dos objetos Entity da classe.
	 */
    Page<EntityType> filtrar(String search, String filter, Pageable pageable);

}
