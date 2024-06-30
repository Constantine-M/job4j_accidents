package ru.job4j.accidents.repository.accident;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * @author Constantine on 19.06.2024
 */
@Slf4j
@Repository
public class AccidentMem implements AccidentRepository {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    /**
     * Инициализацию данных выносим в конструктор
     */
    public AccidentMem() {
        initData();
    }

    /**
     * Найти все инциденты.
     * @return список инцидентов.
     */
    @Override
    public List<Accident> findAll() {
        return accidents.values()
                .stream()
                .toList();
    }

    /**
     * Найти по ID.
     *
     * @param id идентификатор инцидента.
     * @return {@link Optional}.
     */
    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    /**
     * Открыть/создать инцидент.
     *
     * @param accident инцидент.
     */
    @Override
    public Accident save(Accident accident) {
        accident.setId(accidents.size() + 1);
        accidents.putIfAbsent(accident.getId(), accident);
        return accident;
    }

    /**
     * Удалить по ID.
     *
     * @param id идентификатор инцидента.
     */
    @Override
    public void deleteById(int id) {
        accidents.remove(id);
    }

    /**
     * Обновить инцидент.
     *
     * Используем {@link Map#computeIfPresent(Object, BiFunction)}.
     * Если находим инцидент по ID, то перезаписываем
     * в эту ячейку инцидент с новыми данными.
     */
    @Override
    public boolean updateAccident(Accident accident) {
        return accidents.computeIfPresent(
                accident.getId(), (id, oldAccident) -> new Accident(
                oldAccident.getId(),
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType()
                )
        ) != null;
    }

    /**
     * Метод временно заполняет данными
     * нашу коллекцию, чтобы в представлении
     * было что показать.
     */
    private void initData() {
        accidents.putIfAbsent(1, new Accident(1,
                "Accident name 1", "Accident text 1", "Accident address 1",
                new AccidentType(1, "Two cars")));
        accidents.putIfAbsent(2, new Accident(2,
                "Accident name 2", "Accident text 2", "Accident address 2",
                new AccidentType(1, "Two cars")));
        accidents.putIfAbsent(3, new Accident(3,
                "Accident name 3", "Accident text 3", "Accident address 3",
                new AccidentType(1, "Two cars")));
        accidents.putIfAbsent(4, new Accident(4,
                "Accident name 4", "Accident text 4", "Accident address 4",
                new AccidentType(1, "Two cars")));
        accidents.putIfAbsent(5, new Accident(5,
                "Accident name 5", "Accident text 5", "Accident address 5",
                new AccidentType(1, "Two cars")));
    }
}
