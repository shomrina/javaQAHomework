package otus.steps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import otus.pages.LKpersonalDataPage;
import otus.pages.MainPage;

public class BaseTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(BaseTest.class);

    MainPage mainPage = new MainPage(driver);
    LKpersonalDataPage lKpersonalDataPage;


}
