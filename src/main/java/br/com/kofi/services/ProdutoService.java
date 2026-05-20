package br.com.kofi.services;

import br.com.kofi.models.Produto;
import br.com.kofi.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // READ — listar todos
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // READ — buscar um por id
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    // CREATE e UPDATE — save() serve para os dois (se tem id = update, se não tem = create)
    public void salvar(Produto produto) {
        produtoRepository.save(produto);
    }

    // DELETE
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
}