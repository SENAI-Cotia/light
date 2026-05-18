package br.com.kofi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController {

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
	public String cardapio() {
		return "pages/cardapio";
	}

	@GetMapping("/atendimento")
	public String atendimento() {
		return "pages/atendimento";
	}

	@GetMapping("/cozinha")
	public String cozinha() {
		return "pages/cozinha";
	}

	@GetMapping("/configuracoes")
	public String configuracoes() {
		return "pages/configuracoes";
	}
}
