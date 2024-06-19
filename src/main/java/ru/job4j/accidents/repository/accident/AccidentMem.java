package ru.job4j.accidents.repository.accident;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Constantine on 19.06.2024
 */
@Repository
public class AccidentMem implements AccidentRepository {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    /**
     * Найти все инциденты.
     * @return список инцидентов.
     */
    @Override
    public List<Accident> findAll() {
        initData();
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
     * Метод временно заполняет данными
     * нашу коллекцию, чтобы в представлении
     * было что показать.
     */
    private void initData() {
        for (int id = 1; id < 6; id++) {
            accidents.putIfAbsent(id,
                    new Accident(id, "accident".concat(String.valueOf(id)),
                            "text accident".concat(String.valueOf(id)),
                            "accident address".concat(String.valueOf(id))
                    )
            );
        }
    }
}
