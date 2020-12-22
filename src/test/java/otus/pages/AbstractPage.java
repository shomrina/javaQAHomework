package otus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPage {
    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitleThisPage() {
        return driver.getTitle();
    }

    public WebElement waitVisibilityOfElement(By locator, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitUntilElementToBeClickable(WebElement webElement, int timeOutInSeconds) {
        return (new WebDriverWait(driver, timeOutInSeconds)).until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void waitUntilUrlToBe(String urlPage, int timeOutInSeconds) {
        new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.urlToBe(urlPage));
    }

    public String getTextElement(WebElement element) {
        return element.getText();
    }

    public String getAttributeValueElement(WebElement element) {
        return element.getAttribute("value");
    }

    public void clearAndSendKeys(WebElement webElement, String value) {
        webElement.clear();
        webElement.sendKeys(value);
    }

}
