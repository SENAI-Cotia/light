package br.com.kofi.controllers;


import br.com.kofi.models.enums.Papel;
import br.com.kofi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    // TODO: Inserir lógica de criação de usuário dentro do sistema (admin)

    // TODO: Verificar lógica de cookies no Security

    @GetMapping("/cadastrar")
    public String exibirFormulario() {
        return "pages/cadastrar"; // nome do template Thymeleaf
    }

    @PostMapping("/cadastrar")
    public String cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam Papel papel) {

        userService.cadastrar(nome, email, senha, papel);
        return "redirect:/login";
    }
}
