package ru.okpdmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.dto.ClassificatorLinkDto;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.impl.ClassificatorItemService;

import java.util.List;

/**
 * Created by Vladislav on 02.11.2016.
 */
@RestController
@RequestMapping("/update")
public class ClassificatorUpdateController {

    private final ClassificatorService classificatorService;
    private final ClassificatorItemService classificatorItemService;

    @Autowired
    public ClassificatorUpdateController(ClassificatorService classificatorService,
                                         ClassificatorItemService classificatorItemService) {
        this.classificatorService = classificatorService;
        this.classificatorItemService = classificatorItemService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<List<Classificator>> putClassificator(@RequestBody final Classificator model) {
        return new ResponseEntity<>(classificatorService.put(model), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/items", method = RequestMethod.PUT)
    public ResponseEntity<List<ClassificatorItem>> putClassificatorItem(@RequestBody final ClassificatorItem item,
                                                                        @PathVariable(value = "id") String classificatorId) {
        classificatorItemService.addItem(classificatorId, item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/{itemId}/links", method = RequestMethod.PUT)
    public ResponseEntity<List<ClassificatorItem>> putClassificatorLink(@RequestBody final ClassificatorLinkDto linkDto,
                                                                        @PathVariable(value = "id") String classificatorId,
                                                                        @PathVariable(value = "itemId") String itemId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public ResponseEntity<Object> commit() {
        classificatorService.commitClassificators();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
