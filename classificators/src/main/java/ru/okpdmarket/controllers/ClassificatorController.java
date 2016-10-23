package ru.okpdmarket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.okpdmarket.services.ClassificatorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Vladislav on 31.08.2016.
 */
@RestController
@RequestMapping("/classificators")
public class ClassificatorController {

    @Autowired
    ClassificatorService classificatorService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ClassificatorTypeDto> getClassificatorTypes() {
        return classificatorService.getClassificatorTypes();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Classificator addClassificator (@RequestBody final Classificator model,
                                           HttpServletRequest request, HttpServletResponse response) {
        return null;
    }


    @RequestMapping(value = "/{id}/{parentId}", method = RequestMethod.GET)
    public List<ClassificatorItem> getItems(@PathVariable(value="id") String classificatorId,
                                            @PathVariable(value="parentId") String parentId) {
        if (parentId!=null)
            return classificatorService.getClassifiactor(classificatorId).getChildLevel(parentId);
        else
            return classificatorService.getClassifiactor(classificatorId).getFirstLevel();
    }



    @RequestMapping(value = "/{id}/search", method = RequestMethod.GET)
    public List<ClassificatorItem> search(@PathVariable(value="id")String classificatorId, @RequestParam String query) {
//TODO: Lucene in-memory search invoked from ClassificatorService
        return null;
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public List<Classificator>  exportClassificators(){


        return null;
    }


}
