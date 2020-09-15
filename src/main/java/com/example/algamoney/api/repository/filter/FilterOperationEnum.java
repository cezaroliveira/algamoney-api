package com.example.algamoney.api.repository.filter;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

/**
 * Enum contendo todas as operações mapeadas do código gerado pelo templete do
 * Uikit no Angular.
 * 
 * Para utilizar, basta invocar o método
 * {@link #build(CriteriaBuilder, Root, String, Object)} a partir da operação
 * desejada, passando os parâmetros necessários para obter o {@link Predicate}
 * equivalente.
 */
public enum FilterOperationEnum {

	EQUAL("eq", (builder, key, value) -> builder.equal(key, value), "Valor idêntico a."),
	NOT_EQUAL("ne", (builder, key, value) -> builder.notEqual(key, value) ,"Valor diferente de."),

	LESS_THAN("lt", (builder, key, value) -> builder.lessThan(key, value), "Valor menor que."),
	LESS_THAN_OR_EQUAL("le", (builder, key, value) -> builder.lessThanOrEqualTo(key, value), "Valor menor ou igual a."),

	GREATER_THAN("gt", (builder, key, value) -> builder.greaterThan(key, value), "Valor maior que."),
	GREATER_THAN_OR_EQUAL("ge", (builder, key, value) -> builder.greaterThanOrEqualTo(key, value), "Valor maior ou igual a."),

	CONTAINS("contains", (builder, key, value) -> builder.like(key, builder.literal("%" + value + "%")),"Contém o valor."),
	NOT_CONTAINS("not_contains", (builder, key, value) -> builder.notLike(key, builder.literal("%" + value + "%")), "Não contém o valor."),

	STARTS_WITH("startswith", (builder, key, value) -> builder.like(key, builder.literal(value + "%")), "Inicia com o valor."),
	ENDS_WITH("endswith", (builder, key, value) -> builder.like(key, builder.literal("%" + value)), "Termina com o valor.");

	private final String operacao;
	private final FilterPredicateFunction predicateGenerator;
	private final String descricao;

	FilterOperationEnum(String operacao, FilterPredicateFunction predicateGenerator, String descricao) {
		this.operacao = operacao;
		this.predicateGenerator = predicateGenerator;
		this.descricao = descricao;
	}

	/**
	 * Retorna um {@link Predicate} a partir da operação desejada.
	 * 
	 * @param builder
	 * @param rootEntity
	 * @param key
	 * @param value
	 * @return {@link Predicate} equivalente à operação desejada.
	 */
	public Predicate build(CriteriaBuilder builder, Root<?> rootEntity, String key, Object value) {
		return predicateGenerator.predicate(builder, rootEntity.get(key), value.toString());
	}

	static FilterOperationEnum parse(String str) {
		for (FilterOperationEnum filter : FilterOperationEnum.values()) {
			if (StringUtils.equals(str, filter.getOperacao())) {
				return filter;
			}
		}

		throw new WrongFilterException(String.format("Filter operation '%s' not found", str));
	}

	/**
	 * Retorna um {@link FilterOperationEnum} contendo o {@link Predicate}
	 * equivalente à operação informada.
	 * 
	 * @param operacao operação (eq, lt, gt, etc)
	 * @return {@link FilterOperationEnum} contendo o {@link Predicate} equivalente
	 *         à operação informada.
	 */
	public static FilterOperationEnum getPorOperacao(String operacao) {
		return Arrays.stream(FilterOperationEnum.values()).filter(
				item -> item.getOperacao().equals(operacao)).findFirst().get();
	}

	@FunctionalInterface
	interface FilterPredicateFunction {
		Predicate predicate(CriteriaBuilder builder, Path<String> key, String value);
	}

	public String getOperacao() {
		return operacao;
	}

	public String getDescricao() {
		return descricao;
	}

}