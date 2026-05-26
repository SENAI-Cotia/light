package br.com.kofi.services;

import br.com.kofi.models.ItemPedido;
import br.com.kofi.models.Pedido;
import br.com.kofi.models.Produto;
import br.com.kofi.models.enums.StatusPedido;
import br.com.kofi.repositories.PedidoRepository;
import java.util.List;

import br.com.kofi.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<Pedido> listarTodos() {
		return pedidoRepository.findAll();
	}

	public Pedido buscarPorId(Long id) {
		return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
	}

	public void finalizar(Long id) {
		Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
		pedido.setStatus(StatusPedido.PRONTO);
		pedidoRepository.save(pedido);
	}

	public List<Pedido> buscarPorStatus(StatusPedido status) {
		return pedidoRepository.findByStatus(status);
	}

	public void deletar(Long id) {
		pedidoRepository.deleteById(id);
	}

	public void salvar(Pedido pedido) {
		for (ItemPedido item : pedido.getItens()) {
			item.setPedido(pedido);

			Produto produto = produtoRepository.findById(item.getProduto().getId())
					.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
			item.setPrecoUnitario(produto.getPreco());

			produto.setEstoqueAtual(produto.getEstoqueAtual() - item.getQuantidade());
			produtoRepository.save(produto);
		}
		pedidoRepository.save(pedido);
	}
}
