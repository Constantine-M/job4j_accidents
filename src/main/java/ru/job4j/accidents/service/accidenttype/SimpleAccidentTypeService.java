package ru.job4j.accidents.service.accidenttype;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.accidenttype.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Constantine on 23.06.2024
 */
@AllArgsConstructor
@Service
public class SimpleAccidentTypeService implements AccidentTypeService {

    @Qualifier("accidentTypeJdbcTemplate")
    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public List<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}
