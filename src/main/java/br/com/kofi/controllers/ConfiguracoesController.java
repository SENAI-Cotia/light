package br.com.kofi.controllers;

import br.com.kofi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
            RedirectAttributes redirectAttributes) {

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("erroSenha", "As senhas não coincidem.");
            return "redirect:/configuracoes";
        }

        try {
            userService.alterarSenha(authentication.getName(), currentPassword, newPassword);
            redirectAttributes.addFlashAttribute("sucessoSenha", "Senha alterada com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erroSenha", e.getMessage());
        }

        return "redirect:/configuracoes";
    }
}
