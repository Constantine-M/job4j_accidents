package ru.job4j.accidents.repository.rule;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Constantine on 30.06.2024
 */
public interface RuleRepository {

    List<Rule> findAll();

    Set<Rule> findAllByIds(String[] ids);

    Set<Rule> findAllByAccident(Accident accident);

    Optional<Rule> findById(int id);
}
