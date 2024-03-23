package com.example.ShopNet.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Получение информации о статусе ошибки
        Object status = request.getAttribute( RequestDispatcher.ERROR_STATUS_CODE);

        // Если статус ошибки 403, возвращаем страницу с сообщением Forbidden
        if (status != null) {
            int statusCode = Integer.parseInt( status.toString() );
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error403"; // Путь к вашей странице с сообщением о Forbidden
            }
        }

        // В остальных случаях возвращаем страницу с сообщением об ошибке по умолчанию
        return "error";
    }


    public String getErrorPath() {
        return "/error";
    }
}

