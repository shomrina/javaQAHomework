package otus.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import otus.pages.LoginPage;


public class MainPageSteps extends BaseTest {
    private Logger logger = LogManager.getLogger(MainPageSteps.class);


    @When("^User opens main page$")
    public void userOpensMainPage() {
        mainPage.open();
    }

    @Then("^Title of page is right.$")
    public void titleOfPageIsRight() {
        Assert.assertEquals(mainPage.getTitleMainPage(), mainPage.getTitleThisPage());
    }

    @Given("^Browser is started$")
    public void browserIsStarted() {
        Assert.assertNotNull(driver);
    }

    @And("fills personal credential data: {string}, {string}")
    public void fillsPersonalCredentialData(String login, String password) {
        LoginPage loginPage = mainPage.openLoginPage();
        loginPage.auth(login, password);
    }

    @And("^signs in personal account$")
    public void signsInPersonalAccount() {
        lKpersonalDataPage = mainPage.enterLK();
        logger.info("Выполнен вход на сайт и авторизация в личном кабинете");
    }


}
