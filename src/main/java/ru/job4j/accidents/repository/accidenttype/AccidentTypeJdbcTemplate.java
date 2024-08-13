package ru.job4j.accidents.repository.accidenttype;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

/**
 * @author Constantine on 06.07.2024
 */
@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate implements AccidentTypeRepository {

    private JdbcTemplate jdbc;

    @Override
    public List<AccidentType> findAll() {
        return jdbc.query("SELECT id, acc_type_name FROM accident_type",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("acc_type_name"));
                    return accidentType;
                });
    }

    /**
     * Найти тип инцидента по ID.
     *
     * Метод {@link JdbcTemplate#queryForObject}
     * возвращает null. Чтобы этого избежать,
     * результат обернём в {@link Optional}.
     *
     * @param id идентификатор типа инцидента.
     */
    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject("SELECT id, acc_type_name FROM accident_type WHERE id = ?",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("acc_type_name"));
                    return accidentType;
                },
                id));
    }
}
