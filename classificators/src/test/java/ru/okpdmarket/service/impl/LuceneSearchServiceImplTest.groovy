package ru.okpdmarket.service.impl

import ru.okpdmarket.model.Classificator
import ru.okpdmarket.model.ClassificatorItem
import ru.okpdmarket.repository.impl.ClassificatorContents
import spock.lang.Specification

/**
 * Created by vladislav on 06/03/2017.
 */
class LuceneSearchServiceImplTest extends Specification {

    Classificator classificator
    LuceneSearchServiceImpl service

    def setup() {
        service = new LuceneSearchServiceImpl()
        classificator = new Classificator()
        classificator.setCode("OKPD")
        classificator.setName("ОКПД")
        classificator.setDescription("my OKPD classificator")
        classificator.setContents(new ClassificatorContents(classificator))
        def item = new ClassificatorItem("1", "first elements", "my first test element")
        def item2 = new ClassificatorItem("2", "cats", "Кошки тут")
        classificator.getContents().putItem(item)
        classificator.getContents().putItem(item2)
    }

    def "Index Classificator"() {

        expect:
        service.idx.listAll().length == 0
        service.indexClassificator(classificator)
        service.idx.listAll().length > 0

    }

    def "Search By Classificator"() {
        service.indexClassificator(classificator)

        when:
        def res = service.searchByClassificator("OKPD", "кошки")
        then:
        1.equals(res.size())
        "2".equals(res.get(0).getCode())
    }
}
