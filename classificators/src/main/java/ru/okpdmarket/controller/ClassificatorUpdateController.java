package ru.okpdmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.dto.ClassificatorItemDto;
import ru.okpdmarket.model.dto.ClassificatorLinkDto;
import ru.okpdmarket.service.ClassificatorService;

import java.util.List;

/**
 * Created by Vladislav on 02.11.2016.
 */
@RestController
@RequestMapping("/update")
public class ClassificatorUpdateController {

    private final ClassificatorService classificatorService;

    @Autowired
    public ClassificatorUpdateController(ClassificatorService classificatorService) {
        this.classificatorService = classificatorService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<List<ClassificatorTypeDto>> putClassificator(@RequestBody final ClassificatorTypeDto model) {
        return new ResponseEntity<>(ClassificatorTypeDto.Converter.toDtoList(classificatorService.put(model)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/{itemId}", method = RequestMethod.PUT)
    public ResponseEntity<List<ClassificatorItem>> putClassificatorItem(@RequestBody final ClassificatorItemDto itemDto,
                                                                        @PathVariable(value = "id") String classificatorId,
                                                                        @PathVariable(value = "itemId") String itemId) {
        //TODO: Implement me!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/{itemId}/links", method = RequestMethod.PUT)
    public ResponseEntity<List<ClassificatorItem>> putClassificatorItem(@RequestBody final ClassificatorLinkDto linkDto,
                                                                        @PathVariable(value = "id") String classificatorId,
                                                                        @PathVariable(value = "itemId") String itemId) {
        //TODO: Implement me!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public ResponseEntity<Object> commit() {
        //TODO: Implement me!
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
