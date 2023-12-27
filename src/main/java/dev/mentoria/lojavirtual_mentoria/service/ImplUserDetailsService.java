package dev.mentoria.lojavirtual_mentoria.service;

import dev.mentoria.lojavirtual_mentoria.model.Usuario;
import dev.mentoria.lojavirtual_mentoria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplUserDetailsService implements UserDetailsService {


    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = repository.findUsuarioByLogin(username); // Recebe o login para consulta

        if (usuario == null){
            throw new UsernameNotFoundException("Usuario: " + username + " não foi encontrado!");
        }

        return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
    }


}
