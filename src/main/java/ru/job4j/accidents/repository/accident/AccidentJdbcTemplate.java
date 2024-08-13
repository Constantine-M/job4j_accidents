package ru.job4j.accidents.repository.accident;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.rowmapper.AccidentRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Constantine on 04.07.2024
 */
@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<Accident> findAll() {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        var sql = """
                 SELECT * FROM accidents AS acc
                 LEFT JOIN accident_type AS at ON acc.accident_type_id = at.id
                 LEFT JOIN accidents_rules AS ar ON acc.id = ar.accident_id
                 LEFT JOIN rules ON ar.acc_rule_id = rules.id
                 """;
        jdbc.query(sql, new AccidentRowMapper(accidentMap));
        return accidentMap.values().stream().toList();
    }

    /**
     * Найти инцидент по ID.
     *
     * В методе я не присоединял таблицу rules,
     * т.к. в дальнейшем при выполнении запроса
     * падает 500-ая ошибка в случае, если у
     * инцидента несколько статей.
     *
     * Судя по всему причина в методе
     * {@link JdbcTemplate#queryForObject},
     * который возвращает один объект и у этого
     * объекта вложенные объекты могут быть
     * только в единственном числе.
     *
     * @param id идентификатор инцидента
     * @return Optional<Accident>
     */
    @Override
    public Optional<Accident> findById(int id) {
        String sql = """
                SELECT acc.id, acc_name, acc_text, acc_address, accident_type_id FROM accidents AS acc
                LEFT JOIN accident_type AS at ON acc.accident_type_id = at.id
                WHERE acc.id = ?
                """;
        return Optional.ofNullable(jdbc.queryForObject(sql,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("acc_name"));
                    accident.setText(rs.getString("acc_text"));
                    accident.setAddress(rs.getString("acc_address"));
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("accident_type_id"));
                    accident.setType(accidentType);
                    return accident;
                },
                id));
    }

    /**
     * Сохранить инцидент.
     *
     * Сохранение происходит в 2 этапа:
     * 1.Сохранение инцидента
     * 2.Связывание инцидента и статей
     * Получаем 2 запроса.
     *
     * Чтобы получить ID инцидента и
     * использовать в следующем запросе,
     * воспользуемся {@link KeyHolder}.
     * Т.к. в нашем запросе много ключей,
     * то получаем все ключи и нужный вытаскиваем.
     *
     * @param accident сохраняемый инцидент.
     */
    @Override
    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var sql = "INSERT INTO accidents (acc_name, acc_text, acc_address, accident_type_id) VALUES (?, ?, ?, ?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        var keyMap = keyHolder.getKeys();
        var accidentGeneratedId = keyMap.get("id");

        for (Rule rule : accident.getRules()) {
            jdbc.update("INSERT INTO accidents_rules (accident_id, acc_rule_id) VALUES (?, ?)",
                    accidentGeneratedId, rule.getId()
            );
        }
        return accident;
    }

    @Override
    public void deleteById(int id) {

    }

    /**
     * Обновить инцидент.
     *
     * Если данные в таблице accidents
     * можно обновить стандартным способом,
     * то данные в промежуточной таблице
     * accidents_rules так обновить не удастся,
     * т.к. их может стать больше или
     * меньше. Поэтому я решил провести
     * обновление в 2 этапа:
     * 1.Удаление строк, связанных с инцидентом
     * 2.Вставка новых строк, связанных с инцидентом
     *
     * @param accident обновляемый инцидент
     * @return true если обновление успешно прошло
     */
    @Override
    public boolean updateAccident(Accident accident) {
        var sql = """
                UPDATE accidents
                SET acc_name = ?, acc_text = ?, acc_address = ?, accident_type_id = ?
                WHERE id = ?
                """;
        var updatedAccident = jdbc.update(sql,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        ) != 0;
        jdbc.update("DELETE FROM accidents_rules WHERE accident_id = ?", accident.getId());
        for (Rule rule : accident.getRules()) {
            jdbc.update("INSERT INTO accidents_rules (accident_id, acc_rule_id) VALUES (?, ?)",
                    accident.getId(), rule.getId()
            );
        }
        return updatedAccident;
    }
}
