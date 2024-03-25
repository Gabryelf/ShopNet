package com.example.ShopNet.controllers;

import com.example.ShopNet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для обработки запросов, связанных с аутентификацией и входом пользователей.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    /**
     * Отображает страницу входа.
     *
     * @return Путь к странице входа.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    /**
     * Обрабатывает вход пользователя.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @param model    Модель для передачи атрибутов в представление.
     * @return Путь для перенаправления в зависимости от результата аутентификации.
     */

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.authenticate(username, password)) {
            // Authentication successful, redirect to "/products"
            return "redirect:/products";
        } else {
            // Authentication failed, redirect back to the login page with an error message
            model.addAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
    }


}
