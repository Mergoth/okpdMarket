package ru.okpdmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.SearchService;

import java.util.List;

/**
 * Created by Vladislav on 31.08.2016.
 */
@RestController
public class ClassificatorController {

    private final ClassificatorService classificatorService;

    private final SearchService searchService;

    @Autowired
    public ClassificatorController(ClassificatorService classificatorService, SearchService searchService) {
        this.classificatorService = classificatorService;
        this.searchService = searchService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Classificator> getClassificatorTypes() {
        return classificatorService.getClassificatorTypes();
    }


    @RequestMapping(value = "/{classificatorCode}", method = RequestMethod.GET)
    public List<ClassificatorItem> getTopItems(@PathVariable(value = "classificatorCode") String classificatorCode) {
        return classificatorService.getClassificatorFirstLevel(classificatorCode);
    }

    /**
     * @param classificatorCode - id of the classificator
     * @param itemCode - code of the ClassificatorItem to get
     * @return ClassificatorItem -
     */
    @RequestMapping(value = "/{classificatorCode}/{itemCode}", method = RequestMethod.GET)
    public ClassificatorItem getItem(@PathVariable(value = "classificatorCode") String classificatorCode,
                                     @PathVariable(value = "itemCode") String itemCode) {
        return classificatorService.getItem(classificatorCode, itemCode);
    }

    @RequestMapping(value = "/{code}/search", method = RequestMethod.GET)
    public List<ClassificatorItem> search(@PathVariable(value = "code") String classificatorCode, @RequestParam String query) {
        return searchService.searchByClassificator(classificatorCode, query);
    }
}
