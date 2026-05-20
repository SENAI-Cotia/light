package br.com.kofi.controllers;

import br.com.kofi.models.Pedido;
import br.com.kofi.models.Produto;
import br.com.kofi.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/")
	public String index() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login() {
		return "pages/login";
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "pages/dashboard";
	}

	@GetMapping("/cardapio")
	public String cardapio(Model model) {
		model.addAttribute("produtos", produtoService.listarTodos());
		return "pages/cardapio"; // aponta para templates/produtos/lista.html
	}

	//	@GetMapping("/atendimento")
	//	public String atendimento(Model model) {
	//		model.addAttribute("produtos", produtoService.listarTodos());
	//		return "pages/atendimento";
	//	}

	@GetMapping("/cozinha")
	public String cozinha() {
		return "pages/cozinha";
	}

	@GetMapping("/configuracoes")
	public String configuracoes() {
		return "pages/configuracoes";
	}
}
