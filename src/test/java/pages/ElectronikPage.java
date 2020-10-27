package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElectronikPage extends BasePage {
    private By smartphoneLocator = By.cssSelector("[href^='/catalog--smartfony/']");                            // пункт меню Смартфоны          By.xpath("//a[contains(@href, '/catalog--smartfony/')]");

    public ElectronikPage(WebDriver driver) {
        super(driver);
    }

    public SmartphonesPage openSmartphonesPage() {
        waitVisibilityElementAndClick(smartphoneLocator, 10);
        return new SmartphonesPage(driver);
    }
}
