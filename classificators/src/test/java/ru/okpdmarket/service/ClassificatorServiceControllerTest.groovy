package ru.okpdmarket.service

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

    void testGetItemByCode() {
        /*  Classificator okpd = new Classificator("test1", "Test Name")
          okpd.add("01", "Продукция и услуги сельского хозяйства и охоты")
          okpd.add("01.1", "Культуры однолетние")
          def itemByCode = okpd.getItemByCode("01.1")
          assert(itemByCode)
          assertEquals("Got something wrong by code!","Культуры однолетние",itemByCode.name)*/
    }

    void testName() {
        /*      Classificator okpd = new Classificator("test2", "Test2 Name")
              okpd.add("01", "Продукция и услуги сельского хозяйства и охоты")
              okpd.add("01.1", "Культуры однолетние")
              okpd.add("02", "Продукция лесоводства, лесозаготовок и связанные с этим услуги")
              assertEquals("Wrong size computation",3,okpd.size())*/
    }
}
