package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.exception.RepositoryException;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.accident.AccidentService;
import ru.job4j.accidents.util.ExceptionUtil;

/**
 * @author Constantine on 20.06.2024
 */
@AllArgsConstructor
@Controller
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping
    public String findAllAccidents(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "/accidents/all";
    }

    @GetMapping("/saveAccident")
    public String getCreationPage(Model model) {
        return "/accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String saveAccident(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/";
    }

    /**
     * Обновить инцидент по ID.
     *
     * Аннотация {@link RequestParam} позволяет
     * получить параметр из строки запроса.
     */
    @GetMapping("/updateAccident")
    public String getEditAccidentPage(Model model,
                           @RequestParam("id") int id) {
        var optionalAccident = accidentService.findById(id);
        if (optionalAccident.isEmpty()) {
            model.addAttribute("error", "Accident not found");
            return "/errors/404";
        }
        model.addAttribute("accident", optionalAccident.get());
        return "/accidents/update";
    }

    @PostMapping("/updateAccident")
    public String updateAccident(@ModelAttribute Accident accident,
                                 Model model) {
        try {
            accidentService.update(accident);
        } catch (Exception e) {
            if (ExceptionUtil.getRootCause(e) instanceof RepositoryException) {
                model.addAttribute("error", e.getMessage());
                return "/errors/404";
            } else if (ExceptionUtil.getRootCause(e) instanceof ServiceException) {
                model.addAttribute("error", e.getMessage());
                return "/errors/404";
            }
        }
        return "redirect:/";
    }
}
