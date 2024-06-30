package ru.job4j.accidents.repository.accidenttype;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Constantine on 23.06.2024
 */
@Repository
public class AccidentTypeMem implements AccidentTypeRepository {

    private final List<AccidentType> accidentTypes = new ArrayList<>();

    public AccidentTypeMem() {
        initAccidentTypes();
    }

    @Override
    public List<AccidentType> findAll() {
        return accidentTypes;
    }

    /**
     * Найти тип инцидента по ID.
     *
     * Помним о том, что индексация начинается
     * с 0, поэтому во время поиска вычитаем 1.
     *
     * @param id идентификатор типа инцидента.
     */
    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id - 1));
    }

    private void initAccidentTypes() {
        accidentTypes.add(new AccidentType(1, "Two cars"));
        accidentTypes.add(new AccidentType(2, "Car and person"));
        accidentTypes.add(new AccidentType(3, "Car and cyclist"));
    }
}
