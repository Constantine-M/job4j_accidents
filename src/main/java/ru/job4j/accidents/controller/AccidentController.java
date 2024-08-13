package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.exception.ControllerException;
import ru.job4j.accidents.exception.ServiceException;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.accident.AccidentService;
import ru.job4j.accidents.service.accidenttype.AccidentTypeService;
import ru.job4j.accidents.service.rule.RuleService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Constantine on 20.06.2024
 */
@Slf4j
@AllArgsConstructor
@Controller
public class AccidentController {

    private final AccidentService accidentService;

    private final AccidentTypeService accidentTypeService;

    private final RuleService ruleService;

    @GetMapping
    public String findAllAccidents(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "/accidents/all";
    }

    @GetMapping("/saveAccident")
    public String getCreationPage(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "/accidents/createAccident";
    }

    /**
     * Сохранить инцидент.
     *
     * 1.Берем из запроса список ID статей,
     * которые связаны с инцидентом.
     * 2.Полученную информацию с фронта
     * передаем в слой сервисов.
     *
     * @param accident инцидент.
     * @param request запрос.
     */
    @PostMapping("/saveAccident")
    public String saveAccident(@ModelAttribute Accident accident,
                               HttpServletRequest request) {
        String[] accidentRuleIds = request.getParameterValues("rids");
        accidentService.save(accident, accidentRuleIds);
        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String getDetailInfo(Model model,
                                @PathVariable int id) {
        var optionalAccident = accidentService.findById(id);
        if (optionalAccident.isEmpty()) {
            model.addAttribute("error", "Accident not found!");
            return "/errors/404";
        }
        model.addAttribute("accident", optionalAccident.get());
        model.addAttribute("rules", ruleService.findAllByAccident(optionalAccident.get()));
        return "accidents/detail";
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
            model.addAttribute("error", "Accident not found!");
            return "/errors/404";
        }
        model.addAttribute("accident", optionalAccident.get());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "/accidents/update";
    }

    @PostMapping("/updateAccident")
    public String updateAccident(@ModelAttribute Accident accident,
                                 HttpServletRequest request) throws ServiceException, ControllerException {
        String[] accidentRuleIds = request.getParameterValues("rids");
        accidentService.update(accident, accidentRuleIds);
        return "redirect:/";
    }
}
