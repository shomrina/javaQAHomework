package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MainPage extends AbstractPage {
    private Logger logger = LogManager.getLogger(MainPage.class);

    private String baseURL = "https://otus.ru/";
    private String titleMainPage = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";

    private By buttonEnterLKlocator = By.cssSelector("button.header2__auth");
    private By avatarLocator = By.cssSelector(".ic-blog-default-avatar");
    private By myProfileButtonLocator = By.cssSelector("a[href='/lk/biography/personal/'] > div > b");


    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(baseURL);
        logger.info("Открыта страница отус {}", baseURL);
    }

    public String getTitleMainPage() {
        return titleMainPage;
    }

    public LoginPage openLoginPage() {
        driver.findElement(buttonEnterLKlocator).click();
        return new LoginPage(driver);
    }

    //todo сделать отдельный класс под главную страницу с авторизацией? или так оставить?
    public LKpersonalDataPage enterLK() {
        WebElement avatar = waitVisibilityOfElement(avatarLocator, 5);
        Actions actions = new Actions(driver);
        actions.moveToElement(avatar).build().perform();
        driver.findElement(myProfileButtonLocator).click();  //click by MY PROFILE
        logger.info("Перешли в личный кабинет");
        return new LKpersonalDataPage(driver);
    }


}
