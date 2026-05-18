package br.com.kofi.services;

import br.com.kofi.models.Usuario;
import br.com.kofi.models.enums.Papel;
import br.com.kofi.repositories.UsuarioRepository;


import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void cadastrar(String nome, String email, String senha, Papel papel){
        Usuario user = new Usuario();
        user.setEmail(email);
        user.setNome(nome);
        user.setPapel(papel);
        user.setSenhaHash(passwordEncoder.encode(senha));

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Credenciais inválidas"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getSenhaHash())
                .roles(user.getPapel().toString())
                .build();
    }
}