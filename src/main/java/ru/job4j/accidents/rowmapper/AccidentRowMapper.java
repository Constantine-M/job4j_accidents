package ru.job4j.accidents.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Constantine on 05.07.2024
 */
public class AccidentRowMapper implements RowMapper<Accident> {

    Map<Integer, Accident> accidentMap;

    public AccidentRowMapper(Map<Integer, Accident> accidentMap) {
        this.accidentMap = accidentMap;
    }

    @Override
    public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
        return getAccidentByResultSet(accidentMap, rs);
    }

    /**
     * Метод собирает модель Accident из ResultSet.
     * Не содержит Set<Rule>.
     *
     * @param resultSet результат поиска
     * @return инцидент
     */
    private Accident getAccidentByResultSet(Map<Integer, Accident> accidentMap,
                                            ResultSet resultSet) throws SQLException {
        int accidentId = resultSet.getInt("id");
        Accident accident = accidentMap.get(accidentId);

        if (accident == null) {
            accident = new Accident();
            accident.setId(accidentId);
            accident.setName(resultSet.getString("acc_name"));
            accident.setText(resultSet.getString("acc_text"));
            accident.setAddress(resultSet.getString("acc_address"));
            AccidentType accidentType = new AccidentType(
                    resultSet.getInt("accident_type_id"),
                    resultSet.getString("acc_type_name"));
            accident.setType(accidentType);
        }

        int ruleId = resultSet.getInt("acc_rule_id");
        setRuleToAccidentByResultSet(ruleId, resultSet, accident);

        accidentMap.put(accidentId, accident);

        return accident;
    }

    /**
     * Метод добавляет модель Rule в Accident
     * из общего запроса ResultSet
     *
     * @param ruleId идентификатор статьи
     * @param resultSet результат поиска
     * @param accident  инцидент
     */
    private void setRuleToAccidentByResultSet(int ruleId, ResultSet resultSet, Accident accident) throws SQLException {
        if (ruleId != 0) {
            Rule rule = new Rule();
            rule.setId(ruleId);
            rule.setName(resultSet.getString("acc_rule_name"));
            accident.addRule(rule);
        }
    }
}
