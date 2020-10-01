package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.BaseEntity;
import com.example.algamoney.api.repository.CopyProperties;
import com.example.algamoney.api.repository.filter.FilterQuery;
import com.example.algamoney.api.service.CrudService;

/**
 * Caso seja necessário sobrescrever algum dos mapeamentos de URL existentes com
 * um método diferente (nome, parâmetros, retorno, etc), basta sobrescrevê-lo e
 * mapear uma outra URL, para evitar conflito.
 * 
 * Para mapear para URL diferente ou incluir permissões para uma mesma
 * assinatura, é necessário sobrescrever um método integralmente, incluindo as
 * anotações dos parâmetros.
 * 
 * Já as anotações do método são herdadas desta abstração
 * (@GetMapping, @PostMapping, etc)
 * 
 * Exemplo de inclusão de permissão: <br>
 * 
 * @PreAuthorize(value = "hasRole('ROLE_PESQUISAR_CATEGORIA')") public
 *                     ResponseEntity<Page<EntityType>> filtrar(
 * @RequestParam(value = "$search", required = false) String search,
 * @RequestParam(value = "$filter", required = false) String filter, Pageable
 *                     pageable) {...}
 * 
 * @param <EntityType>       Entidade do recurso
 * @param <EntityRepository> Repositório da entidade
 * @param <EntityService>    Serviço da entidade
 */
public abstract class AbstractCrudResource<EntityType extends BaseEntity, EntityRepository extends JpaRepository<EntityType, Long> & CopyProperties & FilterQuery<EntityType>, EntityService extends CrudService<EntityType, EntityRepository>> {

	@Autowired
	protected EntityRepository entityRepository;

	@Autowired
	protected EntityService entityService;

	@Autowired
	protected ApplicationEventPublisher eventPublisher;

	@GetMapping(path = "/listar")
	public List<EntityType> listar() {
		return entityRepository.findAll();
	}

	/**
	 * Retorna os registros na base aplicando a paginação, ordenação e filtro.
	 * 
	 * @param search   representa a busca rápida que pode ser aplicada a vários
	 *                 campos
	 * @param filter   contém todos os campos, operadores e valores
	 * @param pageable
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Page<EntityType>> filtrar(
			@RequestParam(value = "$search", required = false) String search,
			@RequestParam(value = "$filter", required = false) String filter,
			Pageable pageable) {

		Page<EntityType> pageEntityDTO = entityService.filtrar(search, filter, pageable);

		return /* pageEntityDTO.isEmpty() ? ResponseEntity.noContent().build() : */ResponseEntity.ok(pageEntityDTO);
	}

	@PostMapping
	public ResponseEntity<EntityType> criar(@Valid @RequestBody EntityType entity, HttpServletResponse response) {

		EntityType entityNaBase = entityService.criar(entity);

		// Publica o evento responsável por gerar no header a localização para o objeto que foi salvo
		// TODO: Verificar como manter esse código genérico
		eventPublisher.publishEvent(new RecursoCriadoEvent(this, response, entityNaBase.getCodigo()));

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