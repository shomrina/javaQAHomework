package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElectronicPage extends BasePage {
    private By smartphoneLocator = By.cssSelector("[href^='/catalog--smartfony/']");                            // пункт меню Смартфоны          By.xpath("//a[contains(@href, '/catalog--smartfony/')]");

    public ElectronicPage(WebDriver driver) {
        super(driver);
    }

    public SmartphonesPage openSmartphonesPage() {
        waitVisibilityElement(smartphoneLocator, 10).click();
        return new SmartphonesPage(driver);
    }
}
