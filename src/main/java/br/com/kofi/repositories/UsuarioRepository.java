package br.com.kofi.repositories;

import br.com.kofi.models.Usuario;
import br.com.kofi.models.enums.Papel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findAllByAtivo(boolean ativo);

    List<Usuario> findAllByPapel(Papel papel);

}