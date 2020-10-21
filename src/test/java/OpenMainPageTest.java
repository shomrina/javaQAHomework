import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

public class OpenMainPageTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(OpenMainPageTest.class);

    @Before
    public void setUp() {
        //ввод опций через пробел внутри кавычек(если опций несколько), например: mvn clean test -Dbrowser="chrome" -Doptions="window-size=1920,1080 incognito" (для линукса кавычки одинарные)
        driver = WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));

        //driver = WebDriverFactory.create("chrome");  //для запуска из ИДЕ (ручная отладка) или строкой выше, но тогда параметры будут браться из пом-файла
        logger.info("Драйвер поднят");
    }

    @Test
    public void verifyTitleMainPage() {
        String expectedTitle = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
        driver.get("https://otus.ru/");
        logger.info("Открыта страница отус");
        assertEquals(expectedTitle, driver.getTitle());
        logger.info("Проверка Title страницы завершена с успешным результатом");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
