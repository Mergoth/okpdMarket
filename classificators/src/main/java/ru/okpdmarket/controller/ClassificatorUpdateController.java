package ru.okpdmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.ClassificatorUpdateService;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Vladislav on 02.11.2016.
 */
@RestController
@RequestMapping("/update/classificators")
public class ClassificatorUpdateController {


    private final ClassificatorUpdateService classificatorUpdateService;


    @Autowired
    public ClassificatorUpdateController(ClassificatorUpdateService classificatorUpdateService) {
        this.classificatorUpdateService = classificatorUpdateService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<List<Classificator>> putClassificator(@RequestBody final Classificator model,
                                                                HttpServletRequest request, HttpServletResponse response) {

        return new ResponseEntity<List<Classificator>>(classificatorUpdateService.put(model), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}/item", method = RequestMethod.PUT)
    public ResponseEntity<List<ClassificatorItem>> putClassificatorItem(@RequestBody final ClassificatorItem item, @PathVariable(value = "id") String parentCode, String previousCode,
                                                                        HttpServletResponse response) {

        return new ResponseEntity<List<ClassificatorItem>>(classificatorUpdateService.putItem(item), HttpStatus.CREATED);
    }
}
