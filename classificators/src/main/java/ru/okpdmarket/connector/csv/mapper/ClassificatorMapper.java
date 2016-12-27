package ru.okpdmarket.connector.csv.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import ru.okpdmarket.model.Classificator;

/**
 * Created by frostymaster on 25.12.2016.
 */
public class ClassificatorMapper implements FieldSetMapper<Classificator> {


    @Override
    public Classificator mapFieldSet(FieldSet fieldSet) throws BindException {
        Classificator classificator = new Classificator(fieldSet.readString(0),fieldSet.readString(1));
        return classificator;
    }
}
