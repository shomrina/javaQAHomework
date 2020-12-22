package otus.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import otus.steps.BaseTest;
import otus.util.WebDriverFactory;

public class Hooks extends BaseTest {
    private Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() {
        //ввод опций через пробел внутри кавычек(если опций несколько), например: mvn clean test -Dbrowser="chrome" -Doptions="window-size=1920,1080 incognito" (для линукса кавычки одинарные)
        //или пример mvn clean test -Dbrowser="chrome" -Doptions="start-maximized" -Dlogin="log" -Dpassword="pass"
       // driver = WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));
        driver = WebDriverFactory.create("chrome", "start-maximized");  //почемуто не читает проперти из помника(((

        //driver = otus.util.WebDriverFactory.create("chrome");  //для запуска из ИДЕ (ручная отладка) или строкой выше, но тогда параметры будут браться из пом-файла
        logger.info("Драйвер поднят");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Текущие сессия и браузер закрыты");
    }

/*    public static WebDriver driver;*/
/*    private Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() {
        //ввод опций через пробел внутри кавычек(если опций несколько), например: mvn clean test -Dbrowser="chrome" -Doptions="window-size=1920,1080 incognito" (для линукса кавычки одинарные)
        //или пример mvn clean test -Dbrowser="chrome" -Doptions="start-maximized" -Dlogin="log" -Dpassword="pass"
        driver = WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));

        //driver = otus.util.WebDriverFactory.create("chrome");  //для запуска из ИДЕ (ручная отладка) или строкой выше, но тогда параметры будут браться из пом-файла
        logger.info("Драйвер поднят");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Текущие сессия и браузер закрыты");
    }*/
}
