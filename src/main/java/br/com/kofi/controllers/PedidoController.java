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

	@GetMapping("/novo")
	public String formularioNovo(Model model) {
		Pedido pedido = new Pedido();
		pedido.getItens().add(new ItemPedido());

		model.addAttribute("pedido", pedido);
		model.addAttribute("produtos", produtoService.listarTodos());
		return "pages/novo-pedido";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute Pedido pedido) {

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
		model.addAttribute("produtos", produtoService.listarTodos());
		return "pedidos/formulario";
	}

	@PostMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id) {
		pedidoService.deletar(id);
		return "redirect:/atendimento";
	}

	private String gerarNumeroPedido() {
		return String.format("#%04d", (int) (Math.random() * 9999));
	}

	@GetMapping("/{pedidoId}/itens/{itemId}/remover")
	public String removerItem(@PathVariable Long pedidoId, @PathVariable Long itemId) {
		Pedido pedido = pedidoService.buscarPorId(pedidoId);

		// remove o item da lista — o orphanRemoval faz o DELETE no banco automaticamente
		pedido.getItens().removeIf(item -> item.getId().equals(itemId));

		pedidoService.salvar(pedido);
		return "redirect:/atendimento/" + pedidoId + "/editar";
	}
}
