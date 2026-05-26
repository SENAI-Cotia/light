package br.com.kofi.controllers;

import br.com.kofi.models.Pedido;
import br.com.kofi.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/atendimento")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute Pedido pedido, RedirectAttributes redirectAttributes) {
		try {
			if (pedido.getNumeroPedido() == null || pedido.getNumeroPedido().isEmpty()) {
				pedido.setNumeroPedido(gerarNumeroPedido());
			}
			pedidoService.salvar(pedido);
			redirectAttributes.addFlashAttribute("sucesso", "Pedido salvo com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao salvar pedido: " + e.getMessage());
		}
		return "redirect:/atendimento";
	}

	@PostMapping("/{id}/concluir")
	public String formularioConcluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			pedidoService.finalizar(id);
			redirectAttributes.addFlashAttribute("sucesso", "Pedido concluído com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao concluir pedido: " + e.getMessage());
		}

		return "redirect:/cozinha";
	}

	@PostMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			pedidoService.deletar(id);
			redirectAttributes.addFlashAttribute("sucesso", "Pedido excluído com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao excluir pedido: " + e.getMessage());
		}
		return "redirect:/atendimento";
	}

	private String gerarNumeroPedido() {
		return String.format("#%04d", (int) (Math.random() * 9999));
	}
}
