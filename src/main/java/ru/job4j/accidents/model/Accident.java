package ru.job4j.accidents.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;

/**
 * @author Constantine on 19.06.2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Accident {

    @Include
    private int id;

    private String name;

    private String text;

    private String address;
}
