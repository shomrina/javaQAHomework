import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class YandexMarketTest extends BaseTest {


    private Logger logger = LogManager.getLogger(YandexMarketTest.class);

    private String startURL = "https://market.yandex.ru/";

    private By elektronikaLocator = By.cssSelector("div[role=tablist] div[data-zone-name]:nth-child(5)");       //пункт меню Электроника
    private By bluLocator = By.xpath("//span[contains(text(), 'Напишите, какой товар вам нужен')]");            //локатор для синей всплывашки, которая загораживает пункты меню By.cssSelector("span[data-tid=ad8ef471]")
    private By smartphoneLocator = By.cssSelector("[href^='/catalog--smartfony/']");                            // пункт меню Смартфоны          By.xpath("//a[contains(@href, '/catalog--smartfony/')]");
    private By makerLocator = By.cssSelector("fieldset[data-autotest-id='7893318']");                           //блок Производитель
    private By productListLocator = By.cssSelector("article[data-autotest-id='product-snippet']");              //блок результатов поиска (находится список)
    private By sortPriceLocator = By.cssSelector("button[data-autotest-id='dprice']");                          //кнопка сортирвки по цене (из состояния если не включена! при вкл локатор меняется)
    private By compareWidgetLocator = By.cssSelector("[data-apiary-widget-id='/content/popupInformer'] > div"); //виджет с инфой о товаре, добавленном к сравнению
    private By compareButtonLocator = By.cssSelector("a[href='/my/compare-lists']");
    private By compareUnitListLocator = By.cssSelector("div[data-apiary-widget-id='/content/compareContent'] img");



    @Test
    public void addedToCompare()  {
        String model1 = "Samsung";
        String model2 = "Xiaomi";

        //1 открыть страницу яндекс маркета
        driver.get(startURL);
        logger.info(String.format("Открыта страница: %s", startURL));

       //2 перейти в Электроника - Смартфоны
        waitVisibilityElementAndClick(bluLocator, 5); // WebDriverWait w1 = new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(bluLocator)); этот способ на 6 сек больше
        driver.findElement(elektronikaLocator).click();
        waitVisibilityElementAndClick(smartphoneLocator, 10);
        logger.info("Перешли в раздел Электроника- Смартфоны");

        //3 Отфильтровать список товаров: Samsung & Xiaomi
        filterByMaker(model1);                                             //Samsung   8
        filterByMaker(model2);                                              //Xiaomi    10
        logger.info(String.format("Список отсортирован по %s и %s",  model1, model2));  // info(«Message {}», param)

        //4 Отсортировать по цене
        driver.findElement(sortPriceLocator).click();
        logger.info("Список отсортирован по цене");

        //5 Добавить первый в списке Samsung
        List<WebElement> productsList = getProductList();
        logger.info("Получен список найденных результатов");
        String samsungModel = addedFirstToCompare(productsList, model1);
        assertTrue(samsungModel.contains(model1));
        logger.info("Добавлен к сравнению первый в списке {}", model1);

        //6 Проверить что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        assertCompareWidgetText(samsungModel);

        //7  Добавить первый в списке Xiaomi
        String xiaomiModel = addedFirstToCompare(productsList, model2);
        assertTrue(xiaomiModel.contains(model2));
        logger.info("Добавлен к сравнению первый в списке {}", model2);

        //8 Проверить что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        assertCompareWidgetText(xiaomiModel);

        //9 Перейти в раздел сравнение
        driver.findElement(compareWidgetLocator).findElement(compareButtonLocator).click();
        waitTitleIs("Сравнение товаров — Яндекс.Маркет", 10);
        logger.info("Перешли в раздел 'Сравнить'");

        //10 Проверить, что в списке товаров две позиции
        List<WebElement> compareUnitsList = driver.findElements(compareUnitListLocator);
        assertEquals(2, compareUnitsList.size());
        logger.info("Проверка, что в списке товаров две позиции успешно завершена");


        assertUnitName(samsungModel, xiaomiModel, compareUnitsList);


        logger.info("Проверка наименований товаров в списке сравнений успешно завершена");
    }

    private void assertUnitName(String model1, String model2, List<WebElement> compareUnitsList) {
        int model1Index = 0;
        int model2Index = 1;
        if (!model1.equals(compareUnitsList.get(model1Index).getAttribute("alt"))) {
            model1Index = 1;
            model2Index = 0;
        }
        assertEquals(model1, compareUnitsList.get(model1Index).getAttribute("alt"));
        assertEquals(model2, compareUnitsList.get(model2Index).getAttribute("alt"));
    }

    private void waitTitleIs(String title, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.titleIs(title));
    }

    private void filterByMaker(String makerName) {
        WebElement makerList = waitVisibilityElement(makerLocator, 5);
      //  WebElement makerList = driver.findElement(makerLocator);
        By makerNameLocator = By.xpath(".//span[contains(text(), '" + makerName + "')]");
        makerList.findElement(makerNameLocator).click();
    }

    private void assertCompareWidgetText(String phoneModel) {
        WebElement compareWidget = waitVisibilityElement(compareWidgetLocator, 10);
        String expectedTextinWidget = "Товар " + phoneModel + " добавлен к сравнению";
        By unitNameInWidgetLocator = By.cssSelector("div > div:nth-child(2) > div:first-child");
    //    String actualTextWidget = driver.findElement(compareWidgetLocator).findElement(unitNameInWidgetLocator).getAttribute("innerText");
        String actualTextWidget = compareWidget.findElement(unitNameInWidgetLocator).getAttribute("innerText");
        assertEquals(expectedTextinWidget, actualTextWidget);
        logger.info("Проверка, что отобразилась плашка с именем товара {} успешно завершена", phoneModel);
    }

    private List<WebElement> getProductList() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(productListLocator));
        //для того чтобы дождаться обновления списка след 4-ре строчки. вейт антил не помог
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(productListLocator).get(0)).build().perform();
        By currentCompareLocator = By.cssSelector("div[data-zone-name='snippetList'] article:nth-child(1) div[aria-label*='сравнению']");
        waitVisibilityElement(currentCompareLocator, 5);
        return driver.findElements(productListLocator);
    }

    private String addedFirstToCompare(List<WebElement> productsList, String phoneModel) {
        Actions action = new Actions(driver);
        for (int i = 0; i < productsList.size(); i++) {
            WebElement phone = productsList.get(i);
            String namePhone = phone.findElement(By.cssSelector("h3 > a")).getAttribute("title");
            if (namePhone.contains(phoneModel)) {
                action.moveToElement(phone).build().perform();
                By currentCompareLocator = By.cssSelector("div[data-zone-name='snippetList'] article:nth-child(" + (i + 1) + ") div[aria-label*='сравнению']");
                waitVisibilityElementAndClick(currentCompareLocator, 5);
                return namePhone;
            }
        }
        return null;
    }

    private void waitVisibilityElementAndClick(By locator, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    private WebElement waitVisibilityElement(By locator, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }



}
