package com.example.ShopNet.controllers;


import com.example.ShopNet.models.User;
import com.example.ShopNet.models.enums.Role;
import com.example.ShopNet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

/**
 * Контроллер для администрирования пользователей.
 * Требует, чтобы пользователь обладал ролью ROLE_ADMIN для доступа ко всем методам контроллера.
 */
@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;

    /**
     * Обрабатывает GET-запросы для пути /admin.
     * Передает список всех пользователей и информацию о текущем пользователе в модель.
     *
     * @param model     Модель для передачи данных в представление.
     * @param principal Объект Principal для получения информации о текущем пользователе.
     * @return Строку, представляющую имя представления "admin".
     */
    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("users", userService.list());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin";
    }

    /**
     * Обрабатывает GET-запросы для пути /admin/user/edit/{user}.
     * Возвращает представление "user-edit" для редактирования пользователя.
     * Передает информацию о пользователе, его ролях и текущем пользователе в модель.
     *
     * @param user      Пользователь для редактирования.
     * @param model     Модель для передачи данных в представление.
     * @param principal Объект Principal для получения информации о текущем пользователе.
     * @return Строку, представляющую имя представления "user-edit".
     */
    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("currentUser", userService.getUserByPrincipal(principal));
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    /**
     * Обрабатывает POST-запросы для пути /admin/user/edit.
     * Изменяет роли пользователя на основе переданных данных формы.
     *
     * @param user Пользователь, чьи роли будут изменены.
     * @param form Данные формы с новыми ролями пользователя.
     * @return Строку перенаправления на /admin.
     */
    @PostMapping("/admin/user/edit")
    public String userEdit(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }

    /**
     * Обрабатывает POST-запросы для пути /admin/user/ban/{id}.
     * Блокирует пользователя с указанным идентификатором.
     *
     * @param id Идентификатор пользователя для блокировки.
     * @return Строку перенаправления на /admin.
     */
    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") Long id) {
        userService.banUser(id);
        return "redirect:/admin";
    }
}

