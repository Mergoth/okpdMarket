package ru.okpdmarket.service

import org.junit.Test
import ru.okpdmarket.model.Classificator

/**
 * Created by Vladislav on 31.08.2016.
 */
class ClassificatorTest extends GroovyTestCase {
   // private Classificator okpd1;
    @Override
    void setUp() {
        super.setUp()
       /* okpd1  = new Classificator();
        okpd1.add("01","Продукция и услуги сельского хозяйства и охоты");
        okpd1.add("01.1","Культуры однолетние");
        okpd1.add("02","Продукция лесоводства, лесозаготовок и связанные с этим услуги");
        okpd1.add("01.11","Культуры зерновые (кроме риса), зернобобовые, семена масличных культур");
*/
    }

    @Test
    void testGetItemByCode() {
        Classificator okpd = new Classificator("test1")
        okpd.add("01", "Продукция и услуги сельского хозяйства и охоты")
        okpd.add("01.1", "Культуры однолетние")
        def itemByCode = okpd.getItemByCode("01.1")
        assert(itemByCode)
        assertEquals("Got something wrong by code!","Культуры однолетние",itemByCode.name)
    }

    @Test
    void testName() {
        Classificator okpd = new Classificator("test2")
        okpd.add("01", "Продукция и услуги сельского хозяйства и охоты")
        okpd.add("01.1", "Культуры однолетние")
        okpd.add("02", "Продукция лесоводства, лесозаготовок и связанные с этим услуги")
        assertEquals("Wrong size computation",3,okpd.size())
    }
}
