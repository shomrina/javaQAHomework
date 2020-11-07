import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import pages.MainPage;

import static org.junit.Assert.assertEquals;

public class MainPageTest extends BaseTest {
    private Logger logger = LogManager.getLogger(MainPageTest.class);

    @Ignore
    @Test
    public void verifyTitleMainPage() {
        MainPage mainPage = new MainPage(driver);
        assertEquals(mainPage.getTitleMainPage(), mainPage.getTitleThisPage());
        logger.info("Проверка Title страницы завершена с успешным результатом");
    }
}
