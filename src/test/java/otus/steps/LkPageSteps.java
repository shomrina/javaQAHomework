package otus.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import otus.hooks.Hooks;
import otus.pages.LKpersonalDataPage;
import otus.pages.MainPage;
import otus.util.WebDriverFactory;

public class LkPageSteps extends BaseTest{
    private Logger logger = LogManager.getLogger(LkPageSteps.class);

    @When("user fill personal data: {string}, {string}, {string}, {string}, {string}")
    public void userFillPersonalData(String firstName, String firstNameLatin, String lastName, String lastNameLatin, String dateOfBirthday) {
        lKpersonalDataPage.fillPersonalData(firstName, firstNameLatin, lastName, lastNameLatin, dateOfBirthday);
    }

    @And("user click button {string}")
    public void userClickButtonSaveAndContinue() {
        lKpersonalDataPage.saveAndContinue();
    }

    @And("^close browser$")
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Текущие сессия и браузер закрыты");
    }

    @And("^User open new browser$")
    public void userOpenNewBrowser() {
        driver = WebDriverFactory.create("chrome", "start-maximized");  //почемуто не читает проперти из помника(((
        logger.info("Драйвер поднят");
    }

    @Then("filled personal data is correct: {string}, {string}, {string}, {string}, {string}")
    public void filledPersonalDataIsCorrect(String firstName, String firstNameLatin, String lastName, String lastNameLatin, String dateOfBirthday) {
        Assert.assertEquals(firstName, lKpersonalDataPage.getAttributeValueElement(lKpersonalDataPage.getFname()));
        Assert.assertEquals(firstNameLatin, lKpersonalDataPage.getAttributeValueElement(lKpersonalDataPage.getFnameLatin()));
        Assert.assertEquals(lastName, lKpersonalDataPage.getAttributeValueElement(lKpersonalDataPage.getLname()));
        Assert.assertEquals(lastNameLatin, lKpersonalDataPage.getAttributeValueElement(lKpersonalDataPage.getLnameLatin()));
        Assert.assertEquals(dateOfBirthday, lKpersonalDataPage.getAttributeValueElement(lKpersonalDataPage.getBirthday()));
    }
}
