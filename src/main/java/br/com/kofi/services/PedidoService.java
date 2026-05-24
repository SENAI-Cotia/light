package br.com.kofi.services;

import br.com.kofi.models.ItemPedido;
import br.com.kofi.models.Pedido;
import br.com.kofi.models.Produto;
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

//	public void salvar(Pedido pedido) {
//		for (ItemPedido item : pedido.getItens()) {
//			item.setPedido(pedido);
//		}
//		pedidoRepository.save(pedido);
//	}

	public void deletar(Long id) {
		pedidoRepository.deleteById(id);
		// orphanRemoval=true no @OneToMany já apaga os itens automaticamente
	}

	public void salvar(Pedido pedido) {
		for (ItemPedido item : pedido.getItens()) {
			item.setPedido(pedido); // conecta o item ao pedido pai

			// snapshot do preço — busca o preço atual do produto e fixa no item
			Produto produto = produtoRepository.findById(item.getProduto().getId())
					.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
			item.setPrecoUnitario(produto.getPreco()); // ← preço travado no momento do pedido
		}
		pedidoRepository.save(pedido);
	}
}
