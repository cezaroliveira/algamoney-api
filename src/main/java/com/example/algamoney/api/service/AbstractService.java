package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractService<EntityType, EntityRepository extends JpaRepository<EntityType, Long>> {

	@Autowired
	protected EntityRepository entityRepository;

	public EntityType criar(EntityType entity) {

		return entityRepository.save(entity);
	}

	public EntityType atualizar(Long codigo, EntityType entity) {

		EntityType entityNaBase = obter(codigo);

		BeanUtils.copyProperties(entity, entityNaBase, "codigo");

		return entityRepository.save(entityNaBase);
	}

	public EntityType obter(Long codigo) {

		Optional<EntityType> entityNaBase = entityRepository.findById(codigo);

		if (!entityNaBase.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}

		return entityNaBase.get();
	}

}