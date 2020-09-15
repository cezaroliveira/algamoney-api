package com.example.algamoney.api.repository;

import org.springframework.beans.BeanUtils;

/**
 * Interface com a implementação padrão para a cópia das propriedades dos beans.
 */
public interface CopyProperties {

	/**
	 * Realiza a cópia das propriedades de um objeto para o outro.
	 * 
	 * @param origem                objeto origem
	 * @param destino               objeto destino
	 * @param propriedadesIgnoradas propriedades a serem ignoradas
	 * @see BeanUtils#copyProperties(Object, Object)
	 */
	default void copyProperties(Object origem, Object destino, String... propriedadesIgnoradas) {
		BeanUtils.copyProperties(origem, destino, propriedadesIgnoradas);
	}

}
