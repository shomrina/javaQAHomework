package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {
    private Logger logger = LogManager.getLogger(LoginPage.class);

    private By emailLocator = By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)");
    private By passwordLocator = By.cssSelector(".js-psw-input");
    private By submitLocator = By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)");

    public LoginPage(WebDriver driver) {
        super(driver);
        waitVisibilityOfElement(emailLocator, 5);
    }

    public void auth(String login, String password) {
        //todo вынести логин и пароль в параметры ввода при запуске теста из консоли


        driver.findElement(emailLocator).sendKeys(login);
        driver.findElement(passwordLocator).sendKeys(password);
        driver.findElement(submitLocator).submit();
        logger.info("Авторизация прошла успешно");
    }
}
