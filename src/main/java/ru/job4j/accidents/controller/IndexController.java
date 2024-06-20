package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Данный класс находится в слое
 * контроллеров и обрабатывает запросы
 * пользователя.
 *
 * Индексный файл ищется по умолчанию
 * веб-сервером, если в URL указан не файл, а каталог.
 *
 * @author Constantine on 18.06.2024
 */
@AllArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute("user", "Consta");
        return "/index";
    }
}
