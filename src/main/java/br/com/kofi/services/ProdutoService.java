package br.com.kofi.services;

import br.com.kofi.models.Produto;
import br.com.kofi.repositories.ItemPedidoRepository;
import br.com.kofi.repositories.ProdutoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public List<Produto> listarTodos() {
		return produtoRepository.findAll();
	}

	public Produto buscarPorId(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
	}

	public void salvar(Produto produto) {
		produtoRepository.save(produto);
	}

	public void deletar(Long id) {
		if (itemPedidoRepository.existsByProdutoId(id)) {
			throw new RuntimeException("Produto não pode ser excluído pois possui pedidos vinculados.");
		}
		produtoRepository.deleteById(id);
	}

	public List<Produto> buscarProdutosEmAlerta() {
		return produtoRepository.findTop10ByOrderByEstoqueAtualAsc();
	}

}
