package br.com.kofi.controllers;

import br.com.kofi.models.Produto;
import br.com.kofi.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String salvar(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {
		try {
			produtoService.salvar(produto);
			redirectAttributes.addFlashAttribute("sucesso", "Produto salvo com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("erro", e.getMessage());
		}

		return "redirect:/cardapio";
	}

	@GetMapping("/{id}/editar")
	public String formularioEditar(@PathVariable Long id, Model model) {
		model.addAttribute("produto", produtoService.buscarPorId(id));
		return "pages/novo-produto";
	}

	@PostMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			produtoService.deletar(id);
			redirectAttributes.addFlashAttribute("sucesso", "Produto excluído com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("erro", e.getMessage());
		}

		return "redirect:/cardapio";
	}
}
