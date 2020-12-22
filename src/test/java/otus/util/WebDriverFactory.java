package otus.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);
    public enum WebDriverName {
        CHROME,
        FIREFOX
    }

    public static WebDriver create(String webDriverName, String options) {
        WebDriverName driverName = WebDriverName.valueOf(webDriverName.toUpperCase());
        WebDriver driver = null;
        String[] browserOptions = new String[0];
        if (options != null)  browserOptions = options.split(" ");

        switch (driverName) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(browserOptions);
                driver = new ChromeDriver(chromeOptions);
                logger.info("Инициализирован ChromeDriver");
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments(browserOptions);
                driver = new FirefoxDriver(firefoxOptions);
                logger.info("Инициализирован FirefoxDriver");
                break;
            default:
                logger.error("Указанный WebDriver не реализован");
        }

        return driver;
    }

    //для локального вызова (не через консоль)
    public static WebDriver create(String webDriverName) {
        return create(webDriverName, null);
    }
}
