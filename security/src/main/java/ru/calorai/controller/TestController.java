package ru.calorai.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String home() {
        return "<a href='/oauth2/authorization/google'>Войти через Google</a>";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User user) {
        return "О, это ты " + user.getAttribute("name") + ". " + "Твой имейл? " + user.getAttribute("email");

    }
}
