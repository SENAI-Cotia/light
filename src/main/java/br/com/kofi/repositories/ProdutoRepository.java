package br.com.kofi.repositories;

import br.com.kofi.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto , Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
