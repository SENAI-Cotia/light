package br.com.kofi.services;

import br.com.kofi.models.Pedido;
import br.com.kofi.models.Produto;
import br.com.kofi.models.Usuario;
import br.com.kofi.models.enums.Papel;
import br.com.kofi.repositories.UsuarioRepository;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Usuario> listarTodos() {
		return userRepository.findAll();
	}

	public void cadastrar(String nome, String email, String senha, Papel papel) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("E-mail já está em uso.");
		}

		Usuario user = new Usuario();
		user.setEmail(email);
		user.setNome(nome);
		user.setPapel(papel);
		user.setSenhaHash(passwordEncoder.encode(senha));

		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
		Usuario user = userRepository
			.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));

		return org.springframework.security.core.userdetails.User
			.builder()
			.username(user.getEmail())
			.password(user.getSenhaHash())
			.roles(user.getPapel().toString())
			.build();
	}

	public void alterarSenha(String email, String senhaAtual, String novaSenha) {
		Usuario user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		if (!passwordEncoder.matches(senhaAtual, user.getSenhaHash())) {
			throw new RuntimeException("Senha atual incorreta");
		}

		user.setSenhaHash(passwordEncoder.encode(novaSenha));
		userRepository.save(user);
	}

	public void atualizar(Long id, String nome, String email, Papel papel) {
		Usuario user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

		userRepository.findByEmail(email)
				.filter(u -> !u.getId().equals(id))
				.ifPresent(u -> { throw new RuntimeException("E-mail já está em uso."); });

		user.setNome(nome);
		user.setEmail(email);
		user.setPapel(papel);

		userRepository.save(user);
	}

	public void deletar(@PathVariable Long id) {
		userRepository.deleteById(id);
	}

}
