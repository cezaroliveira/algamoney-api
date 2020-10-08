package com.example.algamoney.api.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.service.UsuarioService;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario usuario = usuarioService.obterPorEmail(email);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário e/ou senha inválidos!");
		}

		return new UsuarioSistema(usuario, getAuthorities(usuario)); 
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
		return usuario.getPermissoes().stream().map(permissao -> {
			return new SimpleGrantedAuthority(permissao.getDescricao());
		}).collect(Collectors.toSet());
	}

}
