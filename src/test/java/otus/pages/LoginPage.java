package otus.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    private Logger logger = LogManager.getLogger(LoginPage.class);
    private By emailLocator = By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)");

    public LoginPage(WebDriver driver) {
        super(driver);
        waitVisibilityOfElement(emailLocator, 5);
    }

    @FindBy(css = "div.new-input-line_slim:nth-child(3) > input:nth-child(1)")
    private WebElement email;

    @FindBy(css = ".js-psw-input")
    private WebElement password;

    @FindBy(css = "div.new-input-line_last:nth-child(5) > button:nth-child(1)")
    private WebElement submit;

    public void auth(String login, String pass) {
        email.sendKeys(login);
        password.sendKeys(pass);
        submit.submit();
        logger.info("Авторизация прошла успешно");
    }
}
