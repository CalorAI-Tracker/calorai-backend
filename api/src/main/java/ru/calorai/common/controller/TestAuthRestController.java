package ru.calorai.common.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-auth")
@PreAuthorize("isAuthenticated()")
public class TestAuthRestController {

    @GetMapping
    public String checkIsAuthenticated() {
        return "You are logged in! Congratulations!";
    }
}
