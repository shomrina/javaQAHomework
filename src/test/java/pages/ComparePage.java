package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ComparePage extends AbstractPage {
    private By compareUnitListLocator = By.cssSelector("div[data-apiary-widget-id='/content/compareContent'] img");

    public ComparePage(WebDriver driver) {
        super(driver);
    }

    public int getItemsCount() {
        List<WebElement> compareUnitsList = getCompareUnitsList();
        return compareUnitsList.size();
    }

    private List<WebElement> getCompareUnitsList() {
        return driver.findElements(compareUnitListLocator);
    }

    public boolean isPageContainsItem(String model) {
        for (WebElement webElement: getCompareUnitsList()) {
            if (model.equals(webElement.getAttribute("alt"))) return true;
        }
        return false;
    }
}
