package ru.job4j.accidents.service.accident;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.accident.AccidentRepository;
import ru.job4j.accidents.repository.accidenttype.AccidentTypeRepository;
import ru.job4j.accidents.repository.rule.RuleRepository;

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

    private final RuleRepository ruleRepository;

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    /**
     * Сохранить инцидент.
     *
     * Находим статьи, находим тип инцидента.
     * Все сетим в инцидент и сохраняем его.
     *
     * @param accident инцидент.
     * @param ruleIds список идентификаторов статей, которые
     *                связаны с инцидентом.
     */
    @Override
    public Accident save(Accident accident, String[] ruleIds) {
        var optionalAccidentType = accidentTypeRepository.findById(accident.getType().getId());
        accident.setRules(ruleRepository.findAllByIds(ruleIds));
        optionalAccidentType.ifPresent(accident::setType);
        return accidentRepository.save(accident);
    }

    @Override
    public void deleteById(int id) {
        accidentRepository.deleteById(id);
    }

    /**
     * Обновить инцидент.
     *
     * Находим статьи, находим тип инцидента -
     * оно будет либо старым, либо новым.
     * Все сетим в инцидент и обновляем его.
     *
     * @param accident инцидент.
     * @param ruleIds список идентификаторов статей, которые
     *                связаны с инцидентом.
     */
    @Override
    public boolean update(Accident accident, String[] ruleIds) {
        var optionalAccidentType = accidentTypeRepository.findById(accident.getType().getId());
        accident.setRules(ruleRepository.findAllByIds(ruleIds));
        optionalAccidentType.ifPresent(accident::setType);
        return accidentRepository.updateAccident(accident);
    }
}
