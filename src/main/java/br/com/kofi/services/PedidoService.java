package br.com.kofi.services;

import br.com.kofi.models.ItemPedido;
import br.com.kofi.models.Pedido;
import br.com.kofi.repositories.PedidoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public List<Pedido> listarTodos() {
		return pedidoRepository.findAll();
	}

	public Pedido buscarPorId(Long id) {
		return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
	}

	public void salvar(Pedido pedido) {
		for (ItemPedido item : pedido.getItens()) {
			item.setPedido(pedido);
		}
		pedidoRepository.save(pedido);
	}

	public void deletar(Long id) {
		pedidoRepository.deleteById(id);
		// orphanRemoval=true no @OneToMany já apaga os itens automaticamente
	}
}
