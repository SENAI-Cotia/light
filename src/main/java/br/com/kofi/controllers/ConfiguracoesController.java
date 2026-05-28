package br.com.kofi.controllers;

import br.com.kofi.models.enums.Papel;
import br.com.kofi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/configuracoes")
public class ConfiguracoesController {

	@Autowired
	private UserService userService;

	@PostMapping("/alterar-senha")
	public String alterarSenha(
		@RequestParam String currentPassword,
		@RequestParam String newPassword,
		@RequestParam String confirmPassword,
		Authentication authentication,
		RedirectAttributes redirectAttributes
	) {
		if (!newPassword.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("erro", "As senhas não coincidem.");
			return "redirect:/configuracoes";
		}

		try {
			userService.alterarSenha(authentication.getName(), currentPassword, newPassword);
			redirectAttributes.addFlashAttribute("sucesso", "Senha alterada com sucesso.");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao alterar senha: " + e.getMessage());
		}

		return "redirect:/configuracoes";
	}

	@PostMapping("/novo-usuario")
	public String cadastrarFuncionario(
		@RequestParam String nome,
		@RequestParam String email,
		@RequestParam String senha,
		@RequestParam Papel papel,
		RedirectAttributes redirectAttributes
	) {
		try {
			userService.cadastrar(nome, email, senha, papel);
			redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
		}

		return "redirect:/configuracoes";
	}

	@PostMapping("/atualizar-usuario")
	public String atualizarUsuario(
		@RequestParam Long id,
		@RequestParam String nome,
		@RequestParam String email,
		@RequestParam Papel papel,
		RedirectAttributes redirectAttributes
	) {
		try {
			userService.atualizar(id, nome, email, papel);
			redirectAttributes.addFlashAttribute("sucesso", "Usuário atualizado com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar: " + e.getMessage());
		}

		return "redirect:/configuracoes";
	}

	@PostMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			userService.deletar(id);
			redirectAttributes.addFlashAttribute("sucesso", "Usuário removido com sucesso.");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao remover usuário: " + e.getMessage());
		}
		return "redirect:/configuracoes";
	}
}
