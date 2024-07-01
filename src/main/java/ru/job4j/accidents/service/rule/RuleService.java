package ru.job4j.accidents.service.rule;

import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Constantine on 30.06.2024
 */
public interface RuleService {

    List<Rule> findAll();

    Set<Rule> findAllByIds(String[] ids);

    Optional<Rule> findById(int id);
}
