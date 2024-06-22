package ru.job4j.accidents.service.accident;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.accident.AccidentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Constantine on 20.06.2024
 */
@Slf4j
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
        try {
            accidentRepository.updateAccident(accident);
        } catch (Exception e) {
            log.error("Error while updating accident. Service exception!", e);
            log.error(Arrays.toString(e.getStackTrace()));
            throw new ServiceException("Error while updating accident! Service exception!");
        }
    }
}
