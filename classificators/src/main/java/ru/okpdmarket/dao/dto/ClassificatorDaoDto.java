package ru.okpdmarket.dao.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

/**
 * Created by vladislav on 11/12/2016.
 */
@Data
public class ClassificatorDaoDto {

    @Id
    private String id;

    // Unique classificator code in english
    private String code;
    // Classificator name in Russian
    private String name;

    // Classificator description
    private String description;

    private ArrayList<ClassificatorItemDaoDto> tree = new ArrayList<>();

    //TODO: create converters
}
