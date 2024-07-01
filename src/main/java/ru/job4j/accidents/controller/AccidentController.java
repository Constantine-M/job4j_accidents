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
import ru.job4j.accidents.service.rule.RuleService;
import ru.job4j.accidents.util.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;
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
     * 2.Находим связанные статьи и сетим
     * в {@link Accident}.
     *
     * @param accident инцидент.
     * @param request запрос.
     */
    @PostMapping("/saveAccident")
    public String saveAccident(@ModelAttribute Accident accident,
                               HttpServletRequest request) {
        String[] ids = request.getParameterValues("rids");
        accident.setRules(ruleService.findAllByIds(ids));
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
        model.addAttribute("accident", accidentService.findById(id).get());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "/accidents/update";
    }

    @PostMapping("/updateAccident")
    public String updateAccident(@ModelAttribute Accident accident,
                                 Model model,
                                 HttpServletRequest request) throws ServiceException, ControllerException {
        try {
            String[] ids = request.getParameterValues("rids");
            accident.setRules(ruleService.findAllByIds(ids));
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
