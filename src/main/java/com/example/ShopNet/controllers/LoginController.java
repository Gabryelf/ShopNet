package com.example.ShopNet.controllers;

import com.example.ShopNet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

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
