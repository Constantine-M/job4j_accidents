package ru.job4j.accidents.service.accident;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.accident.AccidentRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Constantine on 20.06.2024
 */
@AllArgsConstructor
@Service
public class SimpleAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Accident save(Accident accident) {
        return accidentRepository.save(accident);
    }

    @Override
    public void deleteById(int id) {
        accidentRepository.deleteById(id);
    }

    @Override
    public void update(Accident accident) {
        accidentRepository.updateAccident(accident);
    }
}
