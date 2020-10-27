package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {
    private Logger logger = LogManager.getLogger(MainPage.class);
    private String startURL = "https://market.yandex.ru/";
    private By bluLocator = By.xpath("//span[contains(text(), 'Напишите, какой товар вам нужен')]");            //локатор для синей всплывашки, которая загораживает пункты меню By.cssSelector("span[data-tid=ad8ef471]")
    private By elektronikaLocator = By.cssSelector("div[role=tablist] div[data-zone-name]:nth-child(5)");       //пункт меню Электроника

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(startURL);
        logger.info(String.format("Открыта страница: %s", startURL));
        waitVisibilityElementAndClick(bluLocator, 5); // WebDriverWait w1 = new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(bluLocator)); этот способ на 6 сек больше
    }

    public ElectronikPage openElectronikPage() {
        driver.findElement(elektronikaLocator).click();
        return new ElectronikPage(driver);
    }


}
