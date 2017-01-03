package ru.okpdmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.dto.ClassificatorItemDto;
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


    @RequestMapping(value = "/{classificatorId}", method = RequestMethod.GET)
    public List<ClassificatorItemDto> getTopItems(@PathVariable(value = "classificatorId") String classificatorId) {
        return ClassificatorItemDto.Converter.toDtoList(classificatorService.getClassifiactorFirstLevel(classificatorId), true);
    }

    /**
     * @param classificatorId - id of the classificator
     * @param itemCode - code of the ClassificatorItem to get
     * @return ClassificatorItemDto -
     */
    @RequestMapping(value = "/{classificatorId}/{itemCode}", method = RequestMethod.GET)
    public ClassificatorItemDto getItem(@PathVariable(value = "classificatorId") String classificatorId,
                                        @PathVariable(value = "itemCode") String itemCode) {
        return ClassificatorItemDto.Converter.toDto(classificatorService.getItem(classificatorId, itemCode), true);
    }

    @RequestMapping(value = "/{id}/search", method = RequestMethod.GET)
    public List<ClassificatorItemDto> search(@PathVariable(value = "id") String classificatorId, @RequestParam String query) {
        //TODO: Lucene in-memory search invoked from ClassificatorService
        return ClassificatorItemDto.Converter.toDtoList(searchService.search(classificatorId, query), true);
    }
}
