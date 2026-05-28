package br.com.kofi.repositories;

import br.com.kofi.models.Pedido;
import br.com.kofi.models.enums.StatusPedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	List<Pedido> findByStatus(StatusPedido status);

	List<Pedido> findByNumeroPedidoContainingIgnoreCase(String numeroPedido);

	List<Pedido> findTop6ByOrderByCriadoEmDesc();

	List<Pedido> findAllByOrderByCriadoEmDesc();

}
