package com.example.ShopNet.controllers;

import com.example.ShopNet.models.User;
import com.example.ShopNet.services.FileGateAway;
import com.example.ShopNet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * Контроллер для управления пользователями.
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileGateAway fileGateAway;

    /**
     * Обрабатывает запрос на главную страницу.
     *
     * @return Страница приветствия.
     */

    @GetMapping("/")
    public String home() {
        return "hello";
    }

    /**
     * Отображает профиль пользователя.
     *
     * @param principal Интерфейс, представляющий информацию о текущем пользователе.
     * @param model     Модель, используемая для передачи данных в представление.
     * @return Страница профиля пользователя.
     */

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "profile";
    }

    /**
     * Отображает страницу регистрации пользователя.
     *
     * @param principal Интерфейс, представляющий информацию о текущем пользователе.
     * @param model     Модель, используемая для передачи данных в представление.
     * @return Страница регистрации пользователя.
     */

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }

    /**
     * Обрабатывает запрос на создание нового пользователя.
     *
     * @param user  Новый пользователь, полученный из формы регистрации.
     * @param model Модель, используемая для передачи данных в представление.
     * @return Перенаправление на страницу входа в случае успешной регистрации, иначе страница регистрации с сообщением об ошибке.
     */

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            fileGateAway.writeToFife(user.getName() + ".txt", user.getEmail());
            model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    /**
     * Отображает информацию о пользователе и его продуктах.
     *
     * @param user  Пользователь, чью информацию нужно отобразить.
     * @param model Модель, используемая для передачи данных в представление.
     * @return Страница с информацией о пользователе.
     */

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "user";
    }

    /**
     * Отображает страницу приветствия, доступную только аутентифицированным пользователям.
     *
     * @return Страница приветствия.
     */

    @GetMapping("/hello")
    public String securityUrl() {
        return "hello";
    }
}

