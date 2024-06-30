package ru.job4j.accidents.repository.accident;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

/**
 * @author Constantine on 19.06.2024
 */
public interface AccidentRepository {

    List<Accident> findAll();

    Optional<Accident> findById(int id);

    Accident save(Accident accident);

    void deleteById(int id);

    boolean updateAccident(Accident accident);
}
