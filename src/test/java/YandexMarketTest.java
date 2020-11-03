import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import pages.ComparePage;
import pages.ElectronicPage;
import pages.MainPage;
import pages.SmartphonesPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class YandexMarketTest extends BaseTest {

    private Logger logger = LogManager.getLogger(YandexMarketTest.class);

    @Test
    public void addedToCompare()  {
        String model1 = "Samsung";
        String model2 = "Xiaomi";

        //1 открыть страницу яндекс маркета
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

       //2 перейти в Электроника - Смартфоны
        ElectronicPage electronicPage = mainPage.openElectronicPage();
        SmartphonesPage smartphonesPage = electronicPage.openSmartphonesPage();
        logger.info("Перешли в раздел Электроника- Смартфоны");

        //3 Отфильтровать список товаров: Samsung & Xiaomi
        smartphonesPage.filterByMaker(model1);                                             //Samsung   8
        smartphonesPage.filterByMaker(model2);                                              //Xiaomi    10
        logger.info(String.format("Список отсортирован по %s и %s",  model1, model2));  // info(«Message {}», param)

        //4 Отсортировать по цене
        smartphonesPage.sortByPriceAscending();
        smartphonesPage.waitUntilProductListUpdated();

        //5 Добавить первый в списке Samsung
        String samsungModel = smartphonesPage.addedFirstItemToCompare(model1);
        assertTrue(samsungModel.contains(model1));
        logger.info("Добавлен к сравнению первый в списке {}", model1);

        //6 Проверить что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        assertCompareWidgetText(samsungModel, smartphonesPage);

        //7  Добавить первый в списке Xiaomi
        String xiaomiModel = smartphonesPage.addedFirstItemToCompare(model2);
        assertTrue(xiaomiModel.contains(model2));
        logger.info("Добавлен к сравнению первый в списке {}", model2);

        //8 Проверить что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        assertCompareWidgetText(xiaomiModel, smartphonesPage);

        //9 Перейти в раздел сравнение
        ComparePage comparePage = smartphonesPage.openComparePage();

        //10 Проверить, что в списке товаров две позиции
        assertEquals(2, comparePage.getItemsCount());
        logger.info("Проверка, что в списке товаров две позиции успешно завершена");

        assertTrue("Item " + samsungModel + " not found", comparePage.isPageContainsItem(samsungModel));
        assertTrue("Item " + xiaomiModel + " not found", comparePage.isPageContainsItem(xiaomiModel));
        logger.info("Проверка наименований товаров в списке сравнений успешно завершена");
    }


    private void assertCompareWidgetText(String phoneModel, SmartphonesPage smartphonesPage) {
        String actualTextWidget = smartphonesPage.getUnitNameFromCompareWidget();
        String expectedTextinWidget = "Товар " + phoneModel + " добавлен к сравнению";
        assertEquals(expectedTextinWidget, actualTextWidget);
        logger.info("Проверка, что отобразилась плашка с именем товара {} успешно завершена", phoneModel);
    }


}
