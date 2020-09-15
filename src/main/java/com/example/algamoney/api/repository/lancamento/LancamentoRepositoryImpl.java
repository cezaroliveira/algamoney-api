package com.example.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Lancamento_;
import com.example.algamoney.api.repository.filter.AbstractFilterQuery;

public class LancamentoRepositoryImpl extends AbstractFilterQuery<Lancamento> implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery = criteriaBuilder.createQuery(Lancamento.class);		
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

		// Criar restrições
		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Lancamento> typedQuery = entityManager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, total(lancamentoFilter));
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder criteriaBuilder,
			Root<Lancamento> root) {

		List<Predicate> listPredicates = new ArrayList<>();

		if (StringUtils.isNotEmpty(lancamentoFilter.getDescricao())) {
			listPredicates.add(criteriaBuilder.like(
					criteriaBuilder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}

		if (lancamentoFilter.getDataVencimentoInicio() != null) {
			listPredicates.add(criteriaBuilder.greaterThanOrEqualTo(
					root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoInicio()));
		}

		if (lancamentoFilter.getDataVencimentoFim() != null) {
			listPredicates.add(criteriaBuilder.lessThanOrEqualTo(
					root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoFim()));
		}

		return listPredicates.toArray(new Predicate[listPredicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> typedQuery, Pageable pageable) {

		int numeroPagina = pageable.getPageNumber();
		int registrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = numeroPagina * registrosPorPagina;

		typedQuery.setFirstResult(primeiroRegistroDaPagina);
		typedQuery.setMaxResults(registrosPorPagina);
	}

	private Long total(LancamentoFilter lancamentoFilter) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		criteriaQuery.select(criteriaBuilder.count(root));

		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

}
