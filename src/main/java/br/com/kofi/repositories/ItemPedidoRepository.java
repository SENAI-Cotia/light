package br.com.kofi.repositories;

import br.com.kofi.models.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
	boolean existsByProdutoId(Long produtoId);
}
