package br.com.kofi.repositories;

import br.com.kofi.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto , Long> {
}
