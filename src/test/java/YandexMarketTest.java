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


public class YandexMarketTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(YandexMarketTest.class);

    private By elektronikaLocator = By.cssSelector("div[role=tablist] div[data-zone-name]:nth-child(5)");       //пункт меню Электроника
    private By bluLocator = By.cssSelector("span[data-tid=ad8ef471]");                                          //локатор для синей всплывашки, которая загораживает пункты меню
    private By smartphoneLocator = By.cssSelector("[href^='/catalog--smartfony/']");                           // пункт меню Смартфоны          By.xpath("//a[contains(@href, '/catalog--smartfony/')]");
    private By makerLocator = By.cssSelector("fieldset[data-autotest-id='7893318']");                           //блок Производитель
    private By productListLocator = By.cssSelector("article[data-autotest-id='product-snippet']");              //блок результатов поиска (находится список)
    private By sortPriceLocator = By.cssSelector("button[data-autotest-id='dprice']");                          //кнопка сортирвки по цене (из состояния если не включена! при вкл локатор меняется)
    private By compareLocator = By.cssSelector("div[aria-label*='сравнению']");                                 //кнопка "Добавить к сравнению"
    private By compareWidgetLocator = By.cssSelector("[data-apiary-widget-id='/content/popupInformer'] > div"); //виджет с инфой о товаре, добавленном к сравнению
    private By compareButtonLocator = By.cssSelector("a[href='/my/compare-lists']");
    private By compareUnitListLocator = By.cssSelector("div[data-apiary-widget-id='/content/compareContent'] img");

    @Before
    public void setUp() {
        //ввод опций через пробел внутри кавычек(если опций несколько), например: mvn clean test -Dbrowser="chrome" -Doptions="window-size=1920,1080 incognito" (для линукса кавычки одинарные)
        driver = WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));

        //driver = WebDriverFactory.create("chrome");  //для запуска из ИДЕ (ручная отладка) или строкой выше, но тогда параметры будут браться из пом-файла
        logger.info("Драйвер поднят");
    }

    @Test
    public void addedToCompare() throws InterruptedException {
        //1 открыть страницу яндекс маркета
        driver.get("https://market.yandex.ru/");
        logger.info("Открыта страница яндекс маркета");

       //2 перейти в Электроника - Смартфоны
       // until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(text(), 'Напишите, какой товар вам нужен')]")));

        waitVisibilityElementAndClick(bluLocator, 5);
        driver.findElement(elektronikaLocator).click();
        waitVisibilityElementAndClick(smartphoneLocator, 5);
        logger.info("Перешли в раздел Электроника- Смартфоны");

        //3 Отфильтровать список товаров: Samsung & Xiaomi
        waitVisibilityElement(makerLocator, 5);
        WebElement maker = driver.findElement(makerLocator);
        List<WebElement> makers = maker.findElements(By.tagName("li"));
        makers.get(8).findElement(By.cssSelector("div > span")).click();       //Samsung
        makers.get(10).findElement(By.cssSelector("div > span")).click();      //Xiaomi
        logger.info("Список отфильтрован по Samsung & Xiaomi");

        //4 Отсортировать по цене
        driver.findElement(sortPriceLocator).click();
        logger.info("Список отсортирован по цене");

        //5 Добавить первый в списке Samsung
        List<WebElement> productsList = getProductList();
        logger.info("Получен список найденных результатов");
        String samsungModel = addedFirstToCompare(productsList, "Samsung");
        logger.info("Добавлен к сравнению первый в списке Samsung");

        //6 Проверить что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        waitVisibilityElement(compareWidgetLocator, 10);
        String expectedTextinWidget = "Товар " + samsungModel + " добавлен к сравнению";
        String actualTextWidget = driver.findElement(compareWidgetLocator).findElement(By.cssSelector("div > div:nth-child(2) > div:first-child")).getAttribute("innerText");
        assertEquals(expectedTextinWidget, actualTextWidget);
        logger.info("Проверка, что отобразилась плашка с именем товара " + samsungModel + " успешно завершена");

        //7  Добавить первый в списке Xiaomi
        String xiaomiModel = addedFirstToCompare(productsList, "Xiaomi");
        logger.info("Добавлен к сравнению первый в списке Xiaomi");

        //8 Проверить что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        waitVisibilityElement(compareWidgetLocator, 10);
        String expectedTextinWidget2 = "Товар " + xiaomiModel + " добавлен к сравнению";
        String actualTextWidget2 = driver.findElement(compareWidgetLocator).findElement(By.cssSelector("div > div:nth-child(2) > div:first-child")).getAttribute("innerText");
        assertEquals(expectedTextinWidget2, actualTextWidget2);
        logger.info("Проверка, что отобразилась плашка с именем товара " + xiaomiModel + " успешно завершена");

        //9 Перейти в раздел сравнение
        //By.xpath("//span[contains(text(), 'Сравнить')]")
        driver.findElement(compareWidgetLocator).findElement(compareButtonLocator).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.titleIs("Сравнение товаров — Яндекс.Маркет"));
        logger.info("Перешли в раздел 'Сравнить'");

        //10 Проверить, что в списке товаров две позиции
        List<WebElement> compareUnitsList = driver.findElements(compareUnitListLocator);
        assertEquals(2, compareUnitsList.size());
        logger.info("Проверка, что в списке товаров две позиции успешно завершена");
        assertEquals(samsungModel, compareUnitsList.get(0).getAttribute("alt"));
        assertEquals(xiaomiModel, compareUnitsList.get(1).getAttribute("alt"));
        logger.info("Проверка наименований товаров в списке сравнений успешно завершена");
    }

    private List<WebElement> getProductList() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(productListLocator));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(productListLocator).get(0)).build().perform();
        By currentCompareLocator = By.cssSelector("div[data-zone-name='snippetList'] article:nth-child(1) div[aria-label*='сравнению']");
        waitVisibilityElement(currentCompareLocator, 5);
        return driver.findElements(productListLocator);
    }

    private String addedFirstToCompare(List<WebElement> productsList, String phoneModel) throws InterruptedException {
        Actions action = new Actions(driver);
        for (int i = 0; i < productsList.size(); i++) {
            WebElement phone = productsList.get(i);
            String namePhone = phone.findElement(By.cssSelector("h3 > a")).getAttribute("title");
            logger.debug("for: " + i + " " + namePhone);
            if (namePhone.contains(phoneModel)) {
                action.moveToElement(phone).build().perform();
                By currentCompareLocator = By.cssSelector("div[data-zone-name='snippetList'] article:nth-child(" + (i + 1) + ") div[aria-label*='сравнению']");
                waitVisibilityElement(currentCompareLocator, 5);
                driver.findElement(currentCompareLocator).click();
                return namePhone;
               // return phone.findElement(By.cssSelector("h3 > a")).getAttribute("title");
            }
        }
        return null;
    }

    private void waitVisibilityElementAndClick(By locator, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    private void waitVisibilityElement(By locator, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
