package br.com.kofi.controllers;

import br.com.kofi.models.Pedido;
import br.com.kofi.models.Produto;
import br.com.kofi.models.enums.StatusPedido;
import br.com.kofi.services.PedidoService;
import br.com.kofi.services.ProdutoService;
import br.com.kofi.services.UserService;
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

	@Autowired
	private UserService userService;

	@Autowired
	private PedidoService pedidoService;

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
		return "pages/cardapio";
	}

	@GetMapping("/atendimento")
	public String listar(Model model) {
		model.addAttribute("pedido", new Pedido());
		model.addAttribute("pedidos", pedidoService.listarTodos());
		model.addAttribute("produtos", produtoService.listarTodos());
		return "pages/atendimento";
	}

	@GetMapping("/cozinha")
	public String cozinha(Model model) {
		model.addAttribute("pedidos", pedidoService.buscarPorStatus(StatusPedido.PENDENTE));
		return "pages/cozinha";
	}

	@GetMapping("/configuracoes")
	public String configuracoes(Model model) {
		model.addAttribute("usuarios", userService.listarTodos());

		return "pages/configuracoes";
	}
}
