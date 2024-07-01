package ru.job4j.accidents.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;

/**
 * Данная модель описывает статьи,
 * которые могут быть связаны с инцидентом.
 *
 * @author Constantine on 30.06.2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rule {

    @Include
    private int id;

    private String name;
}
