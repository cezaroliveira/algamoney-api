package com.example.algamoney.api.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.CopyProperties;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery, CopyProperties {

	@Query("SELECT u FROM Usuario u WHERE u.email = :email")
	Usuario obterPorEmail(@Param("email") String email);

}
