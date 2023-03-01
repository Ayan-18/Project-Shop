package com.example.shop.controller;

import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/registration")
public class RegistrationController {
    private final UserService userService;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam Long phone
    ) {
        userService.userSave(name, surname, login, password, email, phone);
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
        context.setAuthentication(authentication);
        return "redirect:/products";
    }
}
