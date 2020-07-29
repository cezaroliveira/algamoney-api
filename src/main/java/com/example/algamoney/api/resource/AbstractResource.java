package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.algamoney.api.service.AbstractService;

public abstract class AbstractResource<EntityType, EntityRepository extends JpaRepository<EntityType, Long>, EntityService extends AbstractService<EntityType, EntityRepository>> {

	@Autowired
	protected EntityRepository entityRepository;

	@Autowired
	protected EntityService entityService;

	@Autowired
	protected ApplicationEventPublisher eventPublisher;

	@GetMapping
	public List<EntityType> listar() {
		return entityRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<EntityType> criar(@Valid @RequestBody EntityType entity, HttpServletResponse response) {

		EntityType entityNaBase = entityRepository.save(entity);

		// Publica o evento responsável por gerar no header a localização para o objeto que foi salvo
		// TODO: Verificar como manter esse código genérico
		//		eventPublisher.publishEvent(new RecursoCriadoEvent(this, response, entityNaBase.getCodigo()));

		// Cria uma resposta com o código 201 e retorna o JSON do objeto que foi salvo no banco
		return new ResponseEntity<EntityType>(entityNaBase, HttpStatus.CREATED);
	}

	@GetMapping(path = "/{codigo}")
	public ResponseEntity<EntityType> buscarPeloCodigo(@PathVariable Long codigo) {

		Optional<EntityType> entityNaBase = entityRepository.findById(codigo);

		return entityNaBase.isPresent() ? ResponseEntity.ok(entityNaBase.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping(path = "/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		entityRepository.deleteById(codigo);
	}

	@PutMapping(path = "/{codigo}")
	public ResponseEntity<EntityType> atualizar(@PathVariable Long codigo, @Valid @RequestBody EntityType entity) {

		EntityType entityNaBase = entityService.atualizar(codigo, entity);

		return ResponseEntity.ok(entityNaBase);
	}

}