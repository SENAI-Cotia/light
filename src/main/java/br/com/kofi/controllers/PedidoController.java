package br.com.kofi.controllers;

import br.com.kofi.models.ItemPedido;
import br.com.kofi.models.Pedido;
import br.com.kofi.services.PedidoService;
import br.com.kofi.services.ProdutoService;
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
	private ProdutoService produtoService;

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute Pedido pedido) {
		System.out.println("AQUI -> " + pedido.getValorTotal());

		if (pedido.getNumeroPedido() == null || pedido.getNumeroPedido().isEmpty()) {
			pedido.setNumeroPedido(gerarNumeroPedido());
		}

		pedidoService.salvar(pedido);
		return "redirect:/atendimento";
	}

	@PostMapping("/{id}/concluir")
	public String formularioConcluir(@PathVariable Long id, Model model) {
		pedidoService.finalizar(id);
		model.addAttribute("pedidos", pedidoService.listarTodos());

		return "redirect:/cozinha";
	}

	@PostMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id) {
		pedidoService.deletar(id);
		return "redirect:/atendimento";
	}

	private String gerarNumeroPedido() {
		return String.format("#%04d", (int) (Math.random() * 9999));
	}
}
