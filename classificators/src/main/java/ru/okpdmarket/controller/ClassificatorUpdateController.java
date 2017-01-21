package ru.okpdmarket.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.dto.ClassificatorLinkDto;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.exception.ClassificatorNotFoundException;
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

    @RequestMapping(value = "/{code}/items", method = RequestMethod.PUT)
    public ResponseEntity<List<ClassificatorItem>> putClassificatorItem(@RequestBody final ClassificatorItem item,
                                                                        @PathVariable(value = "code") String classificatorCode) {
        try {
            return new ResponseEntity<>(classificatorItemService.addItem(classificatorCode, item), HttpStatus.OK);
        } catch (ClassificatorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{code}/{itemId}/links", method = RequestMethod.PUT)
    public ResponseEntity<ClassificatorItem> putClassificatorLink(@RequestBody final ClassificatorLinkDto linkDto,
                                                                  @PathVariable(value = "code") String classificatorCode,
                                                                  @PathVariable(value = "itemId") String itemId) {
        val sourceItem = classificatorService.getItem(classificatorCode, itemId);
        val targetItem = classificatorService.getItem(linkDto.getTargetClassificatorCode(), linkDto.getTargetItemCode());
        classificatorItemService.linkItem(sourceItem, targetItem);
        return new ResponseEntity<>(classificatorItemService.linkItem(sourceItem, targetItem), HttpStatus.OK);
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public ResponseEntity<Object> commit() {
        classificatorService.commitClassificators();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
