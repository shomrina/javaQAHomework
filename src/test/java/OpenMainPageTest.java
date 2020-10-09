import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OpenMainPageTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(OpenMainPageTest.class);

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
    }

    @Test
    public void verifyTitleMainPage() {
        String expectedTitle = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
        driver.get("https://otus.ru/");
        logger.info("Открыта страница отус");
        assertEquals(expectedTitle, driver.getTitle());
        logger.info("Проверка Title страницы завершена");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
