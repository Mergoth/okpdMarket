package ru.okpdmarket.model;

import java.util.HashMap;
import java.util.List;

/**
 * Interface for classificator and work with its items
 * Created by Vladislav on 29.08.2016.
 */

public class Classificator {

    private String name = null;


    public ClassificatorItem getItemByCode(String code){
        return null;
    }

    public List<ClassificatorItem> getChildLevel(String code){
        return null;
    }

    public List<ClassificatorItem> getFirstLevel() {
        return null;
    }

    public int size(){
        return 0;
    }

    public String getName() {
        return name;
    }

}
