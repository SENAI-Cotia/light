package br.com.kofi.controllers;

import br.com.kofi.models.Pedido;
import br.com.kofi.models.Produto;
import br.com.kofi.models.enums.StatusPedido;
import br.com.kofi.repositories.PedidoRepository;
import br.com.kofi.repositories.ProdutoRepository;
import br.com.kofi.services.PedidoService;
import br.com.kofi.services.ProdutoService;
import br.com.kofi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping("/")
public class RootController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private UserService userService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;


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
	public String listar(@RequestParam(required = false) String busca, Model model) {
		List<Produto> produtos;

		if (busca != null && busca.length() >= 3) {
			produtos = produtoRepository.findByNomeContainingIgnoreCase(busca);
		} else {
			produtos = produtoRepository.findAll();
		}

		model.addAttribute("produtos", produtos);
		model.addAttribute("busca", busca);
		return "pages/cardapio";
	}

	@GetMapping("/atendimento")
	public String atendimento(
			@RequestParam(required = false) String busca,
			@RequestParam(required = false, defaultValue = "produtos") String modo,
			Model model) {

		List<Produto> produtos = (busca != null && busca.length() >= 3 && modo.equals("produtos"))
				? produtoRepository.findByNomeContainingIgnoreCase(busca)
				: produtoRepository.findAll();

		List<Pedido> pedidos = (busca != null && busca.length() >= 3 && modo.equals("pedidos"))
				? pedidoRepository.findByNumeroPedidoContainingIgnoreCase(busca)
				: pedidoRepository.findAll();

		model.addAttribute("produtos", produtos);
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("busca", busca);
		model.addAttribute("modo", modo);
		return "pages/atendimento";
	}

	@GetMapping("/cozinha")
	public String cozinha(@RequestParam(required = false) String busca, Model model) {

		List<Pedido> pedidos = (busca != null && busca.length() >= 3)
				? pedidoRepository.findByNumeroPedidoContainingIgnoreCase(busca)
				: pedidoRepository.findAll();

		model.addAttribute("pedidos", pedidos);
		model.addAttribute("busca", busca);
		return "pages/cozinha";
	}

	@GetMapping("/configuracoes")
	public String configuracoes(Model model) {
		model.addAttribute("usuarios", userService.listarTodos());

		return "pages/configuracoes";
	}
}
