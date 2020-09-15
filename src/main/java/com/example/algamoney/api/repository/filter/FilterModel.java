package com.example.algamoney.api.repository.filter;

import java.util.List;

public class FilterModel {

	private String campo;
	private FilterOperationEnum operador;
	private String valor;
	private List<FilterModel> clausulaOu;

	public FilterModel() {}

	public FilterModel(List<FilterModel> clausulaOu) {
		this.clausulaOu = clausulaOu;
	}

	public FilterModel(String campo, FilterOperationEnum operador, String valor) {
		this.campo = campo;
		this.operador = operador;
		this.valor = valor;
	}

	public FilterModel(String campo, FilterOperationEnum operador, String valor, List<FilterModel> clausulaOu) {
		this(campo, operador, valor);
		this.clausulaOu = clausulaOu;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public FilterOperationEnum getOperador() {
		return operador;
	}

	public void setOperador(FilterOperationEnum operador) {
		this.operador = operador;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public List<FilterModel> getClausulaOu() {
		return clausulaOu;
	}

	public void setClausulaOu(List<FilterModel> clausulaOu) {
		this.clausulaOu = clausulaOu;
	}

}
