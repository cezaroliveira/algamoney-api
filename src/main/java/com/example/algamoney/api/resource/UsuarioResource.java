package com.example.algamoney.api.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.usuario.UsuarioRepository;
import com.example.algamoney.api.service.UsuarioService;

@RestController
@RequestMapping(path = "usuarios")
public class UsuarioResource extends AbstractCrudResource<Usuario, UsuarioRepository, UsuarioService> {

}