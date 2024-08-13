package ru.job4j.accidents.repository.rule;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.*;

/**
 * @author Constantine on 30.06.2024
 */
@Repository
public class MemRuleRepository implements RuleRepository {

    private final List<Rule> rules = new ArrayList<>();

    public MemRuleRepository() {
        initRules();
    }

    @Override
    public List<Rule> findAll() {
        return rules;
    }

    /**
     * Найти все статьи по ID.
     *
     * 1.Проходимся по списку ID.
     * 2.Используем готовый метод
     * {@link MemRuleRepository#findById}.
     * 3.Парсим ID, т.к. он пришел из
     * запроса в виде String.
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
     * Поиск аналогичен поиску типов:
     * индекс начинается с 0, поэтому,
     * чтобы найти статью с ID = 1,
     * требуется вычесть 1.
     *
     * @param id идентификатор статьи.
     * @return статья {@link Rule}.
     */
    @Override
    public Optional<Rule> findById(int id) {
        return Optional.of(rules.get(id - 1));
    }

    private void initRules() {
        rules.add(new Rule(1, "Article 1"));
        rules.add(new Rule(2, "Article 2"));
        rules.add(new Rule(3, "Article 3"));
        rules.add(new Rule(4, "Article 4"));
        rules.add(new Rule(5, "Article 5"));
    }

    @Override
    public Set<Rule> findAllByAccident(Accident accident) {
        return accident.getRules();
    }
}
