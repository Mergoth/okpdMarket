package ru.okpdmarket.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.List;

/**
 * Created by Vladislav on 31.08.2016.
 */
@RestController
@RequestMapping("/okpd")
public class ClassificatorServiceController {

    @RequestMapping(value = "items", method = RequestMethod.GET)
    public List<ClassificatorItem> getItems(String classificatorId,String parentId) {
        return null;
    }

    @RequestMapping(value = "types", method = RequestMethod.GET)
    public List<Classificator> getClassificatorTypes(){
        return null;
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public List<ClassificatorItem> search(String query) {
        return null;
    }


}
