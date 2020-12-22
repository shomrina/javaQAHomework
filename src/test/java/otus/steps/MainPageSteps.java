package otus.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import otus.pages.MainPage;

public class MainPageSteps extends BaseTest {
    MainPage mainPage = new MainPage(driver);

    @When("^User opens main page$")
    public void userOpensMainPage() {
        mainPage.open();
    }

    @Then("^Title of page is right.$")
    public void titleOfPageIsRight() {
        Assert.assertEquals(mainPage.getTitleMainPage(), mainPage.getTitleThisPage());
    }

    @Given("Browser is started")
    public void browserIsStarted() {
        Assert.assertNotNull(driver);
    }


}
