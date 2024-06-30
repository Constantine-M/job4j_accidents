package ru.job4j.accidents.service.accident;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.exception.ServiceException;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.accident.AccidentRepository;
import ru.job4j.accidents.repository.accidenttype.AccidentTypeRepository;

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

    private final AccidentTypeRepository accidentTypeRepository;

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
        var optionalAccidentType = accidentTypeRepository.findById(accident.getType().getId());
        accident.setType(optionalAccidentType.get());
        return accidentRepository.save(accident);
    }

    @Override
    public void deleteById(int id) {
        accidentRepository.deleteById(id);
    }

    @Override
    public boolean update(Accident accident) throws ServiceException {
        var optionalAccidentType = accidentTypeRepository.findById(accident.getType().getId());
        if (optionalAccidentType.isPresent()) {
            accident.setType(optionalAccidentType.get());
        } else {
            throw new ServiceException("Can't find accident type! Accident has not been updated!");
        }
        return accidentRepository.updateAccident(accident);
    }
}
