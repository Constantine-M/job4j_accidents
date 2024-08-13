package ru.job4j.accidents.repository.rule;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.*;

/**
 * @author Constantine on 24.07.2024
 */
@Repository
@AllArgsConstructor
public class RulesJdbcTemplate implements RuleRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<Rule> findAll() {
        return jdbc.query("SELECT id, acc_rule_name FROM rules",
                (resultSet, rowNumber) -> {
                    Rule rule = new Rule();
                    rule.setId(resultSet.getInt("id"));
                    rule.setName(resultSet.getString("acc_rule_name"));
                    return rule;
                });
    }

    /**
     * Найти все статьи по ID.
     *
     * 1.Проходимся по списку ID.
     * 2.Используем готовый метод
     * {@link RulesJdbcTemplate#findById}.
     * 3.Парсим ID, т.к. он пришел из
     * запроса в виде String.
     *
     * Метод скопирован из класса {@link MemRuleRepository},
     * т.к. его реализация не изменилась.
     *
     * @param ids список ID статей.
     * @return список статей.
     */
    @Override
    public Set<Rule> findAllByIds(String[] ids) {
        var result = new HashSet<Rule>();
        Arrays.stream(ids).forEach(id -> result.add(findById(Integer.parseInt(id)).get()));
        return result;
    }

    /**
     * Найти статью по ID.
     *
     * Метод {@link JdbcTemplate#queryForObject}
     * возвращает null. Чтобы этого избежать,
     * результат обернём в {@link Optional}.
     *
     * @param id идентификатор статьи.
     */
    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject("SELECT id, acc_rule_name FROM rules WHERE id = ?",
                (resultSet, rowNumber) -> {
            Rule newRule = new Rule();
            newRule.setId(resultSet.getInt("id"));
            newRule.setName(resultSet.getString("acc_rule_name"));
            return newRule;
        },
        id));
    }

    @Override
    public Set<Rule> findAllByAccident(Accident accident) {
        var sql = """
                SELECT r.id, r.acc_rule_name
                FROM rules AS r
                JOIN accidents_rules AS ar ON r.id = ar.acc_rule_id
                WHERE ar.accident_id = ?
                """;
        List<Rule> rules = jdbc.query(sql,
                (resultSet, rowNumber) -> {
                    Rule rule = new Rule();
                    rule.setId(resultSet.getInt("id"));
                    rule.setName(resultSet.getString("acc_rule_name"));
                    return rule;
                },
                accident.getId());
        return new HashSet<>(rules);
    }
}
