package ru.job4j.accidents.service.accident;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

/**
 * @author Constantine on 20.06.2024
 */
public interface AccidentService {

    List<Accident> findAll();

    Optional<Accident> findById(int id);

    Accident save(Accident accident, String[] ruleIds);

    void deleteById(int id);

    boolean update(Accident accident, String[] ruleIds);
}
