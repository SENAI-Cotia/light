package br.com.kofi.controllers;

import br.com.kofi.models.ItemPedido;
import br.com.kofi.models.Pedido;
import br.com.kofi.models.Usuario;
import br.com.kofi.services.PedidoService;
import br.com.kofi.services.ProdutoService;
import br.com.kofi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atendimento")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("pedido", new Pedido());
		model.addAttribute("pedidos", pedidoService.listarTodos());
		model.addAttribute("produtos", produtoService.listarTodos());
		return "pages/atendimento";
	}

	@GetMapping("/novo")
	public String formularioNovo(Model model) {
		Pedido pedido = new Pedido();
		pedido.getItens().add(new ItemPedido());

		model.addAttribute("pedido", pedido);
		model.addAttribute("usuarios", userService.listarTodos());
		model.addAttribute("produtos", produtoService.listarTodos());
		return "pages/novo-pedido";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute Pedido pedido, @RequestParam Long usuarioId) {
		Usuario usuario = userService.buscarPorId(usuarioId);
		pedido.setUsuario(usuario);

		// gera número do pedido se for novo
		if (pedido.getNumeroPedido() == null || pedido.getNumeroPedido().isEmpty()) {
			pedido.setNumeroPedido(gerarNumeroPedido());
		}

		pedidoService.salvar(pedido);
		return "redirect:/atendimento";
	}

	@GetMapping("/{id}/editar")
	public String formularioEditar(@PathVariable Long id, Model model) {
		model.addAttribute("pedido", pedidoService.buscarPorId(id));
		model.addAttribute("usuarios", userService.listarTodos());
		model.addAttribute("produtos", produtoService.listarTodos());
		return "pedidos/formulario";
	}

	@PostMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id) {
		pedidoService.deletar(id);
		return "redirect:/pedidos";
	}

	private String gerarNumeroPedido() {
		return String.format("#%04d", (int) (Math.random() * 9999));
	}
}
