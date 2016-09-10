package ru.okpdmarket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
public class ClassificatorServiceController {

    @Autowired
    ClassificatorService classificatorService;

    @RequestMapping(method = RequestMethod.GET)
    public List<String>  getClassificatorTypes(){
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Classificator addClassificator (@RequestBody final Classificator model,
                                           HttpServletRequest request, HttpServletResponse response) {
        return null;
    }


    @RequestMapping(value = "/{id}/{parentId}", method = RequestMethod.GET)
    public List<ClassificatorItem> getItems(@PathVariable(value="id") String classificatorId,
                                            @PathVariable(value="parentId") String parentId) {
        return null;
    }



    @RequestMapping(value = "/{id}/search", method = RequestMethod.GET)
    public List<ClassificatorItem> search(@PathVariable(value="id")String classificatorId, @RequestParam String query) {

        return null;
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public List<Classificator>  exportClassificators(){


        return null;
    }


}
