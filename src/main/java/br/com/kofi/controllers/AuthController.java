package br.com.kofi.controllers;

import br.com.kofi.models.enums.Papel;
import br.com.kofi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Value("${app.route.password}")
    private String routePassword;

    @GetMapping("/cadastrar")
    public String acessarCadastro(@RequestParam String senha) {
        if(!senha.equals(routePassword)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return "pages/cadastrar";
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
