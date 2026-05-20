package br.com.kofi.repositories;

import br.com.kofi.models.Pedido;
import br.com.kofi.models.enums.StatusPedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	// buscar pedidos de um usuário específico
	List<Pedido> findByUsuarioId(Long usuarioId);
	// buscar por status
	List<Pedido> findByStatus(StatusPedido status);
}
