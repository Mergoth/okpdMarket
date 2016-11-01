package ru.okpdmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.okpdmarket.dto.ClassificatorTypeDto;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.SearchService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Vladislav on 31.08.2016.
 */
@RestController
@RequestMapping("/classificators")
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
    public List<ClassificatorTypeDto> getClassificatorTypes() {
        return classificatorService.getClassificatorTypes();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public List<ClassificatorItem> getTopItems(@PathVariable(value = "id") String classificatorId) {
             return classificatorService.getClassifiactor(classificatorId).getFirstLevel();
    }


    @RequestMapping(value = "/{id}/{parentId}", method = RequestMethod.GET)
    public List<ClassificatorItem> getItems(@PathVariable(value = "id") String classificatorId,
                                            @PathVariable(value="parentId") String parentId) {
            return classificatorService.getClassifiactor(classificatorId).getChildLevel(parentId);
    }

    @RequestMapping(value = "/{id}/search", method = RequestMethod.GET)
    public List<ClassificatorItem> search(@PathVariable(value = "id") String classificatorId, @RequestParam String query) {
        //TODO: Lucene in-memory search invoked from ClassificatorService
        return searchService.search(classificatorId,query);
    }


    //TODO: do we need this? Here?
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public List<Classificator>  exportClassificators(){


        return null;
    }


}
