package ru.job4j.accidents.service.rule;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.rule.RuleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Constantine on 30.06.2024
 */
@AllArgsConstructor
@Service
public class SimpleRuleService implements RuleService {

    private final RuleRepository ruleRepository;

    @Override
    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Set<Rule> findAllByIds(String[] ids) {
        return ruleRepository.findAllByIds(ids);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }
}
