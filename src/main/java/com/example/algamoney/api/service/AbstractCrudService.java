package com.example.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.BaseEntity;
import com.example.algamoney.api.repository.CopyProperties;
import com.example.algamoney.api.repository.filter.FilterQuery;

/**
 * Classe abstrata genérica para CRUD na camada de negócio.
 * 
 * @param <EntityType>       entidade JPA
 * @param <EntityRepository> Repository da entidade
 */
public abstract class AbstractCrudService<EntityType extends BaseEntity, EntityRepository extends JpaRepository<EntityType, Long> & CopyProperties & FilterQuery<EntityType>> implements CrudService<EntityType, EntityRepository> {

	@Autowired
	protected EntityRepository entityRepository;

	@Override
	public List<EntityType> listar() {
		return entityRepository.findAll();
	}

	public Page<EntityType> filtrar(String search, String filter, Pageable pageable) {
		return entityRepository.filtrar(search, filter, pageable);
	}

	public EntityType criar(EntityType entity) {

		return entityRepository.save(entity);
	}

	public EntityType atualizar(Long codigo, EntityType entity) {

		EntityType entityNaBase = obter(codigo);

		BeanUtils.copyProperties(entity, entityNaBase, "codigo");

		return entityRepository.save(entityNaBase);
	}

	@Override
	public void excluir(Long codigo) {
		entityRepository.deleteById(codigo);
	}

	public EntityType obter(Long codigo) {

		Optional<EntityType> entityNaBase = entityRepository.findById(codigo);

		if (!entityNaBase.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}

		return entityNaBase.get();
	}

}