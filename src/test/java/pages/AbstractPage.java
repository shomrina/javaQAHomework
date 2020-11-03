package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {
    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebElement waitVisibilityElement(By locator, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitTitleIs(String title, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.titleIs(title));
    }

}
