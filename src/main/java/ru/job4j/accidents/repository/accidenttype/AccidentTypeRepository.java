package ru.job4j.accidents.repository.accidenttype;

import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

/**
 * @author Constantine on 23.06.2024
 */
public interface AccidentTypeRepository {

    List<AccidentType> findAll();

    Optional<AccidentType> findById(int id);
}
