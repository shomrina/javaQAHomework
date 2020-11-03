package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SmartphonesPage extends BasePage {
    private Logger logger = LogManager.getLogger(SmartphonesPage.class);

    private By makerLocator = By.cssSelector("fieldset[data-autotest-id='7893318']");                           //блок Производитель
    private By sortPriceLocator = By.cssSelector("button[data-autotest-id='dprice']");                          //кнопка сортирвки по цене (из состояния если не включена! при вкл локатор меняется)
    private By productListLocator = By.cssSelector("article[data-autotest-id='product-snippet']");              //блок результатов поиска (находится список)
    private By compareWidgetLocator = By.cssSelector("[data-apiary-widget-id='/content/popupInformer'] > div"); //виджет с инфой о товаре, добавленном к сравнению
    private By compareButtonLocator = By.cssSelector("a[href='/my/compare-lists']");
    private By unitNameInWidgetLocator = By.cssSelector("div > div:nth-child(2) > div:first-child");
    private By firstCompareLocator = By.cssSelector("div[data-zone-name='snippetList'] article:nth-child(1) div[aria-label*='сравнению']");

    public SmartphonesPage(WebDriver driver) {
        super(driver);
    }

    public void filterByMaker(String makerName) {
        WebElement makerList = waitVisibilityElement(makerLocator, 5);
        By makerNameLocator = By.xpath(".//span[contains(text(), '" + makerName + "')]");
        makerList.findElement(makerNameLocator).click();
    }

    //сортировка по возрастанию
    public void sortByPriceAscending() {
        driver.findElement(sortPriceLocator).click();
        logger.info("Список отсортирован по цене");
    }

    public String addedFirstItemToCompare(String model) {
        List<WebElement> productsList = getProductList();
        logger.info("Получен список найденных результатов");
        return addedFirstToCompare(productsList, model);
    }

    private List<WebElement> getProductList() {
        return driver.findElements(productListLocator);
    }

    public void waitUntilProductListUpdated() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(productListLocator));
        //для того чтобы дождаться обновления списка след 4-ре строчки. вейт антил не помог
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(productListLocator).get(0)).build().perform();
        waitVisibilityElement(firstCompareLocator, 5);
    }

    private String addedFirstToCompare(List<WebElement> productsList, String phoneModel) {
        Actions action = new Actions(driver);
        for (int i = 0; i < productsList.size(); i++) {
            WebElement phone = productsList.get(i);
            String namePhone = phone.findElement(By.cssSelector("h3 > a")).getAttribute("title");
            if (namePhone.contains(phoneModel)) {
                action.moveToElement(phone).build().perform();
                By currentCompareLocator = By.cssSelector("div[data-zone-name='snippetList'] article:nth-child(" + (i + 1) + ") div[aria-label*='сравнению']");
                waitVisibilityElement(currentCompareLocator, 5).click();
                return namePhone;
            }
        }
        return null;
    }

    public String getUnitNameFromCompareWidget() {
        WebElement compareWidget = waitVisibilityElement(compareWidgetLocator, 10);
        return compareWidget.findElement(unitNameInWidgetLocator).getAttribute("innerText");
    }

    public ComparePage openComparePage() {
        driver.findElement(compareWidgetLocator).findElement(compareButtonLocator).click();
        waitTitleIs("Сравнение товаров — Яндекс.Маркет", 10);
        logger.info("Перешли в раздел 'Сравнить'");
        return new ComparePage(driver);
    }
}
