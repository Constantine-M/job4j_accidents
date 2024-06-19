package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.accident.AccidentService;

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

    private final AccidentService accidentService;

    @GetMapping({"/index", "/"})
    public String getIndex(Model model) {
        model.addAttribute("user", "Consta");
        model.addAttribute("accidents", accidentService.findAll());
        return "/index";
    }
}
