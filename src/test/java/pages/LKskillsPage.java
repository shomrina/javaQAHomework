package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class LKskillsPage extends AbstractPage {
    private Logger logger = LogManager.getLogger(LKskillsPage.class);

    String urlSkillsPage = "https://otus.ru/lk/biography/skills/";

    public LKskillsPage(WebDriver driver) {
        super(driver);
        waitUntilUrlToBe(urlSkillsPage, 10);
    }
}
