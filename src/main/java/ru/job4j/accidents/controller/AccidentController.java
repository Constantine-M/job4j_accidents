package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.exception.ControllerException;
import ru.job4j.accidents.exception.ServiceException;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.accident.AccidentService;
import ru.job4j.accidents.service.accidenttype.AccidentTypeService;
import ru.job4j.accidents.util.ExceptionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Constantine on 20.06.2024
 */
@Slf4j
@AllArgsConstructor
@Controller
public class AccidentController {

    private final AccidentService accidentService;

    private final AccidentTypeService accidentTypeService;

    @GetMapping
    public String findAllAccidents(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "/accidents/all";
    }

    @GetMapping("/saveAccident")
    public String getCreationPage(Model model) {
        List<AccidentType> types = new ArrayList<>();
        types.add(new AccidentType(1, "Two cars"));
        types.add(new AccidentType(2, "Car and person"));
        types.add(new AccidentType(3, "Car and cyclist"));
        model.addAttribute("types", types);
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
        model.addAttribute("types", accidentTypeService.findAll());
        return "/accidents/update";
    }

    @PostMapping("/updateAccident")
    public String updateAccident(@ModelAttribute Accident accident,
                                 Model model) throws ServiceException, ControllerException {
        try {
            accidentService.update(accident);
        } catch (Exception e) {
            if (ExceptionUtil.getRootCause(e) instanceof ServiceException) {
                log.error("Can't update the accident! Error logged!", e);
                log.error(Arrays.toString(e.getStackTrace()));
                model.addAttribute("error", "Can't update the accident!");
                return "errors/404";
            }
            throw new ControllerException("Can't update the accident!", e);
        }
        return "redirect:/";
    }
}
