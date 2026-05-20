package br.com.kofi.controllers;

import br.com.kofi.models.Produto;
import br.com.kofi.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cardapio")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/novo")
	public String formularioNovo(Model model) {
		model.addAttribute("produto", new Produto());
		return "pages/novo-produto";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute Produto produto) {
		produtoService.salvar(produto);
		return "redirect:/cardapio"; // redireciona para a lista após salvar
	}

	@GetMapping("/{id}/editar")
	public String formularioEditar(@PathVariable Long id, Model model) {
		model.addAttribute("produto", produtoService.buscarPorId(id));
		return "pages/novo-produto"; // reutiliza o mesmo template
	}

	@GetMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id) {
		produtoService.deletar(id);
		return "redirect:/cardapio";
	}
}
