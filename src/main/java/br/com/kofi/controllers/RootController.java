package br.com.kofi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController {

	@GetMapping
	public String index(Model model) {
		return "pages/login";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		return "pages/dashboard";
	}

	@GetMapping("/cardapio")
	public String cardapio(Model model) {
		return "pages/cardapio";
	}

	@GetMapping("/atendimento")
	public String atendimento(Model model) {
		return "pages/atendimento";
	}

	@GetMapping("/cozinha")
	public String cozinha(Model model) {
		return "pages/cozinha";
	}

	@GetMapping("/configuracoes")
	public String configuracoes(Model model) {
		return "pages/configuracoes";
	}
}
