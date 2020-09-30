package com.example.algamoney.api.service;

import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.usuario.UsuarioRepository;

@Service
public class UsuarioService extends AbstractCrudService<Usuario, UsuarioRepository> {

	public Usuario obterPorEmail(String email) {
		return entityRepository.obterPorEmail(email);
	}

}