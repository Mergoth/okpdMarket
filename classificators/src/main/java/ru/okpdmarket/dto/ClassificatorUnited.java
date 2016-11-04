package ru.okpdmarket.dto;

import lombok.Data;
import ru.okpdmarket.model.Classificator;

/**
 * @author Sergey Grishchenko on 03.11.16.
 */
@Data
public class ClassificatorUnited {
    Classificator okpd;
    Classificator okpd2;
    Classificator tnved;
}
