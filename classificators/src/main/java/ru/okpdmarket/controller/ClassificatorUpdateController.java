package ru.okpdmarket.controller;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.dto.ClassificatorLinkDto;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.exception.ClassificatorNotFoundException;
import ru.okpdmarket.service.impl.ClassificatorItemService;

import java.util.List;


@RestController
@RequestMapping("/update")
public class ClassificatorUpdateController {
    private static final Logger log = LoggerFactory.getLogger(ClassificatorUpdateController.class);
    private final ClassificatorService classificatorService;
    private final ClassificatorItemService classificatorItemService;

    @Autowired
    public ClassificatorUpdateController(ClassificatorService classificatorService,
                                         ClassificatorItemService classificatorItemService) {
        this.classificatorService = classificatorService;
        this.classificatorItemService = classificatorItemService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<List<Classificator>> putClassificator(@RequestBody final Classificator classificator) {
        return new ResponseEntity<>(classificatorService.put(classificator), HttpStatus.OK);
    }

    @RequestMapping(value = "/{code}/items", method = RequestMethod.PUT)
    public ResponseEntity<List<ClassificatorItem>> putClassificatorItem(@RequestBody final ClassificatorItem item,
                                                                        @PathVariable(value = "code") String classificatorCode) {
        try {
            return new ResponseEntity<>(classificatorItemService.addItem(classificatorCode.toLowerCase(), item), HttpStatus.OK);
        } catch (ClassificatorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{code}/{itemId}/links", method = RequestMethod.PUT)
    public ResponseEntity<ClassificatorItem> putClassificatorLink(@RequestBody final ClassificatorLinkDto linkDto,
                                                                  @PathVariable(value = "code") String classificatorCode,
                                                                  @PathVariable(value = "itemId") String itemId) {
        val sourceItem = classificatorService.getItem(classificatorCode.toLowerCase(), itemId);
        val targetItem = classificatorService.getItem(linkDto.getTargetClassificatorCode().toLowerCase(), linkDto.getTargetItemCode());
        return new ResponseEntity<>(classificatorItemService.linkItem(sourceItem, targetItem), HttpStatus.OK);
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public ResponseEntity<Object> commit() {
        classificatorService.commitClassificators();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
