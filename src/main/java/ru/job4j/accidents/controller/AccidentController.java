package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.accident.AccidentService;

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
}
