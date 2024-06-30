package ru.job4j.accidents.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;

/**
 * Данная модель описывает
 * типы инцидентов.
 *
 * Например, инцидент, в котором
 * оказались водитель и велосипедист.
 *
 * @author Constantine on 22.06.2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentType {

    @Include
    private int id;

    private String name;
}
