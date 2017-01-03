package ru.okpdmarket.model;

import lombok.Data;


/**
 * Interface for classificator and work with its items
 * Created by Vladislav on 29.08.2016.
 */
@Data
public class Classificator {

    private String id;
    private String code;
    private String name;
    private String description;
}