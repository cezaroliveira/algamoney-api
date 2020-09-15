package com.example.algamoney.api.repository.filter;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.BaseEntity;

/**
 * Classe abstrata genérica para filtrar, paginar e ordenar. O filtro deve ser
 * implementado por cada repository através do método
 * {@link #criarRestricoesDeFiltro(BaseEntityDTO, CriteriaBuilder, Root)}.
 *
 * @param <EntityType> entidade JPA
 */
public abstract class AbstractFilterQuery<EntityType extends BaseEntity> implements FilterQuery<EntityType> {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Retorna a classe referente ao tipo genérico EntityType da classe.
	 * 
	 * @return {@link Class} referente ao tipo genérico EntityType da classe.
	 */
	@SuppressWarnings("unchecked")
	private Class<EntityType> getEntityClass() {
		return (Class<EntityType>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public Page<EntityType> filtrar(String search, String filter, Pageable pageable) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<EntityType> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		Root<EntityType> root = criteriaQuery.from(getEntityClass());

		// Criar restrições
		Predicate[] predicates = criarRestricoesDeFiltro(search, filter, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		adicionarOrdenacao(pageable, criteriaBuilder, criteriaQuery, root);

		TypedQuery<EntityType> typedQuery = entityManager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, total(predicates));
	}

	/**
	 * Retorna um array com as restrições de filtro a serem aplicados na listagem.
	 * 
	 * @param search          representa a busca rápida que pode ser aplicada a
	 *                        vários campos
	 * @param filter          contém todos os campos e seus respectivos
	 * @param criteriaBuilder criado a partir do {@link EntityManager}
	 * @param root            criado a partir do {@link #getEntityClass()}
	 * @return um array com as restrições de filtro a serem aplicados na listagem.
	 */
	protected Predicate[] criarRestricoesDeFiltro(String search, String filter, CriteriaBuilder criteriaBuilder, Root<EntityType> root) {

		List<Predicate> listPredicates = new ArrayList<>();

		List<FilterModel> filtros = filtroEspecifico(filter);

		// TODO: Incluir na lista conforme extraído do 

		listPredicates.addAll(configurarFiltro(root, filtros, criteriaBuilder));

		System.out.println(listPredicates);

		return listPredicates.toArray(new Predicate[listPredicates.size()]);
	}

	/**
	 * Prepara uma lista de {@link FilterModel} para realizar uma pesquisa rápida
	 * geral por todos os campos.
	 * 
	 * @param search parâmetro $search enviado pelo Angular.
	 * @return lista de {@link FilterModel} conforme extraído do parâmetro enviado
	 *         pelo Angular.
	 */
	private List<FilterModel> pesquisaRapidaGeral(String search) {

		// search
		// abc,abc,def

		List<FilterModel> listFilterModels = new ArrayList<>();

		if (search != null) {

			System.out.println("----------------------------------------------------------------");
			System.out.println("Pesquisa rápida...");
			System.out.println("-----------------------------");

			String[] pesquisaRapida = search.split(",");

			//            // Obtém apenas uma vez a lista dos atributos da classe
			//            Method[] methods = ProcessoFisico.class.getDeclaredMethods();

			System.out.println("Tokens");
			for (String token: pesquisaRapida) {

				System.out.println(token);

				//                for (Method method: methods) {
				//
				//                    if (Modifier.isPublic(method.getModifiers())) {
				////                        listPredicates.add(
				////                            criteriaBuilder.like(
				////                                root.get(method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4)),
				////                                "%" + token + "%"));
				//                    }
				//                }
			}
			System.out.println("-----------------------------");
		}

		return listFilterModels;
	}

	/**
	 * Prepara uma lista de {@link FilterModel} para realizar uma pesquisa
	 * específica por campos e seus respectivos operadores e valores.
	 * 
	 * @param filter parâmetro $filter enviado pelo Angular.
	 * @return lista de {@link FilterModel} conforme extraído do parâmetro enviado
	 *         pelo Angular.
	 */
	private List<FilterModel> filtroEspecifico(String filter) {

		// filter
		// (codigoProcessoCnj eq '123' or codigoProcessoCnj eq '456') and (numeroUltimaFolha eq '1')
		// and (id eq '2') and (dataRemessa eq '121212121' or dataRemessa eq '2020-09-02T03:00:00.000Z')

		List<FilterModel> listFilterModelsClausulaAnd = new ArrayList<>();

		System.out.println("----------------------------------------------------------------");
		System.out.println("Filtrando...");
		if (filter != null) {

			Pattern patternGroupAnd = Pattern.compile("(?:\\((.*?)\\))");
			Matcher matcherGroupAnd = patternGroupAnd.matcher(filter);

			Pattern patternSubGroupOr =
					Pattern.compile("(?<campo>\\w+)\\s(?<operador>ge|gt|lt|le|eq|ne|contains|not_contains|startswith|endswith|search)\\s(?:(?:')(?<valor>(?:'|.)*?)')");

			while (matcherGroupAnd.find()) {

				System.out.println("-----------------------------");
				System.out.println("Grupo AND");
				System.out.println("Cláusula AND: " + matcherGroupAnd.group());

				List<FilterModel> listFilterModelsClausulaOr = new ArrayList<>();

				listFilterModelsClausulaAnd.add(new FilterModel(listFilterModelsClausulaOr));

				for (int indexGroupAnd = 1; indexGroupAnd <= matcherGroupAnd.groupCount();
						indexGroupAnd++) {

					System.out.println("--------------");
					System.out.println("SubGrupos OR");
					Matcher matcherSubGroupOr =
							patternSubGroupOr.matcher(matcherGroupAnd.group(indexGroupAnd));

					while (matcherSubGroupOr.find()) {

						System.out.println("------");
						System.out.println("Cláusula OR: " + matcherSubGroupOr.group());
						System.out.println("------");

						System.out.println("Campo: " + matcherSubGroupOr.group("campo"));
						System.out.println("Operador: " + matcherSubGroupOr.group("operador"));
						System.out.println("Valor: " + matcherSubGroupOr.group("valor"));

						listFilterModelsClausulaOr.add(new FilterModel(matcherSubGroupOr.group("campo"), FilterOperationEnum.getPorOperacao(matcherSubGroupOr.group("operador")), 
								matcherSubGroupOr.group("valor")));
					}
				}
			}
		}

		return listFilterModelsClausulaAnd;
	}

	/**
	 * Configura o filtro para a busca.
	 * 
	 * @param rootEntity      Root da entidade de referência
	 * @param filtros         lista com os filtros
	 * @param criteriaBuilder CriteriaBuilder
	 * @return lista de {@link Predicate} conforme os filtros informados.
	 */
	private List<Predicate> configurarFiltro(Root<EntityType> rootEntity, List<FilterModel> filtros, CriteriaBuilder criteriaBuilder) {

		List<Predicate> listPredicatesClausulaE = new ArrayList<Predicate>();

		for (FilterModel filterModel : filtros) {

			if (filterModel.getClausulaOu() != null) {
				List<Predicate> listInterno = configurarFiltro(rootEntity, filterModel.getClausulaOu(), criteriaBuilder);
				listPredicatesClausulaE.addAll(Arrays.asList(criteriaBuilder.or(listInterno.toArray(new Predicate[listInterno.size()]))));
			}

			else {
				Predicate predicate = filterModel.getOperador().build(criteriaBuilder, rootEntity, filterModel.getCampo(), filterModel.getValor());

				//				if (predicate != null && filterModel.getClausulaOu() != null && filterModel.getClausulaOu().size() > 0) {
				//					List<Predicate> listPredicatesClausulaOu = new ArrayList<Predicate>();
				//					listPredicatesClausulaOu.add(predicate);
				//					listPredicatesClausulaOu.addAll(configurarFiltro(rootEntity, filterModel.getClausulaOu(), criteriaBuilder));
				//					predicate = criteriaBuilder.or(listPredicatesClausulaOu.toArray(new Predicate[listPredicatesClausulaOu.size()]));
				//				}

				listPredicatesClausulaE.add(predicate);
			}
		}

		return listPredicatesClausulaE;
	}

	/**
	 * Adiciona uma ordenação à query.
	 * 
	 * @param pageable        contém os parâmetros de paginação
	 * @param criteriaBuilder criado a partir do {@link EntityManager}
	 * @param criteriaQuery   criado a partir do {@link #getEntityClass()}
	 * @param root            criado a partir do {@link #getEntityClass()}
	 */
	private void adicionarOrdenacao(Pageable pageable, CriteriaBuilder criteriaBuilder,
			CriteriaQuery<EntityType> criteriaQuery, Root<EntityType> root) {

		if (pageable.getSort().isSorted()) {
			criteriaQuery.orderBy(pageable.getSort().get().map(order -> order.getDirection().isAscending() ? 
					criteriaBuilder.asc(root.get(order.getProperty())) : criteriaBuilder.desc(root.get(order.getProperty()))).collect(Collectors.toList()));
		}
	}

	/**
	 * Adiciona as restrições de paginação a serem aplicadas na listagem.
	 * 
	 * @param typedQuery
	 * @param pageable   contém os parâmetros de paginação
	 */
	private void adicionarRestricoesDePaginacao(TypedQuery<EntityType> typedQuery, Pageable pageable) {

		int numeroPagina = pageable.getPageNumber();
		int registrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = numeroPagina * registrosPorPagina;

		typedQuery.setFirstResult(primeiroRegistroDaPagina);
		typedQuery.setMaxResults(registrosPorPagina);
	}

	/**
	 * Retorna o total de registros na base que se aplicam ao filtro.
	 * 
	 * @param predicates array com as restrições de filtro a serem aplicados na listagem.
	 * @return {@link Long} referente ao total de registros na base que se aplicam ao filtro.
	 */
	private Long total(Predicate[] predicates) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<EntityType> root = criteriaQuery.from(getEntityClass());

		criteriaQuery.where(predicates);

		criteriaQuery.select(criteriaBuilder.count(root));

		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

}
