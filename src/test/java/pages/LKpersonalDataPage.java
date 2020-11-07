package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public class LKpersonalDataPage extends AbstractPage {
    private Logger logger = LogManager.getLogger(LKpersonalDataPage.class);

    private WebElement buttonAdd;

    private By fnameLocator = By.id("id_fname");
    private By fnameLatinLocator = By.id("id_fname_latin");
    private By lnameLocator = By.id("id_lname");
    private By lnameLatinLocator = By.id("id_lname_latin");
    private By birthdayLocator = By.cssSelector(".input-icon > input:nth-child(1)");
    private By countryLocator = By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)");
    private By cityLocator = By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)");
    private By englishLevelLocator = By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)");
    private By deleteButtonLocator = By.cssSelector("div.container__col_12:nth-child(4) > div:nth-child(2) > button:nth-child(1)");
    private By buttonAddLocator = By.cssSelector("button.lk-cv-block__action:nth-child(6)");
    private By contactTypeButtonLocator = By.cssSelector("div[data-selected-option-class='lk-cv-block__select-option_selected'] span");
    private By contactTypeListLocator = By.cssSelector("div[data-selected-option-class='lk-cv-block__select-option_selected']");
    private By contactValueInputs = By.cssSelector("input[type='text']");
    private By contactsBlockLocator = By.cssSelector("div[data-prefix='contact']");
    private By saveAndContinueButtonLocator = By.xpath("//*[contains(text(), 'Сохранить и продолжить')]");


    public LKpersonalDataPage(WebDriver driver) {
        super(driver);
        waitVisibilityOfElement(fnameLocator, 5);
    }

    public void fillPersonalData(String firstName, String firstNameLatin, String lastName, String lastNameLatin, String dateOfBirthday) {
        //todo refactoring!!!
        driver.findElement(fnameLocator).clear();
        driver.findElement(fnameLocator).sendKeys(firstName);

        driver.findElement(fnameLatinLocator).clear();
        driver.findElement(fnameLatinLocator).sendKeys(firstNameLatin);


        driver.findElement(lnameLocator).clear();
        driver.findElement(lnameLocator).sendKeys(lastName);


        driver.findElement(lnameLatinLocator).clear();
        driver.findElement(lnameLatinLocator).sendKeys(lastNameLatin);


        driver.findElement(birthdayLocator).clear();
        driver.findElement(birthdayLocator).sendKeys(dateOfBirthday);
    }

    public void selectCountry(String selectedCountry) {
        if(!driver.findElement(countryLocator).getText().contains(selectedCountry))
        {
            driver.findElement(countryLocator).click();
            driver.findElement(By.xpath("//*[contains(text(), '" + selectedCountry + "')]")).click();
        }
    }

    public void selectCity(String selectedCity) {
        if(!driver.findElement(cityLocator).getText().contains(selectedCity))
        {
            driver.findElement(cityLocator).click();
            driver.findElement(By.xpath("//*[contains(text(), '" + selectedCity + "')]")).click();
        }
    }

    public void selectEngLevel(String englishLevel) {
        if(!driver.findElement(englishLevelLocator).getText().contains(englishLevel))
        {
            driver.findElement(englishLevelLocator).click();
            driver.findElement(By.xpath("//*[contains(text(), '" + englishLevel + "')]")).click();
        }
    }

    public void deleteAllContacts() {
        List<WebElement> deleteButtons = driver.findElements(deleteButtonLocator);                                          //get all buttons Delete for contact
        for(WebElement deletes : deleteButtons) {                                                                           //click DELETE for all contacts
            deletes.click();
        }
    }

    public void clickButtonAdd() {
        if (buttonAdd == null) buttonAdd = driver.findElement(buttonAddLocator);
        buttonAdd.click();
        logger.info("Нажали 'Добавить'");
    }

    public void addContact(String contactType, String contactValue) {
        WebElement contactsBlock = driver.findElement(contactsBlockLocator);                                                //получение блока способ связи, кнопка удалить и добавить (но если ничего не заполнено)
        WebElement contactButton = contactsBlock.findElement(contactTypeButtonLocator);                                     //кнопка для выбора типа связи
        contactButton.click();                                                                                              //open selected options for contacts
        //выбор типа связи
        List<WebElement> contactSelectedList = contactsBlock.findElements(contactTypeListLocator);
        WebElement contactSelected = contactSelectedList.get(contactSelectedList.size() - 1);

        By contactTypeValueLocator = By.cssSelector("div > div >  button[data-value='" + contactType.toLowerCase() + "']");  //Выбор значения по названию
        WebElement contactTypeValue = waitUntilElementToBeClickable(contactSelected.findElement(contactTypeValueLocator), 5);
        contactTypeValue.click();
        logger.info("Добавлен тип связи: {}", contactType);

        //ввод значения для выбранного типа связи
        List<WebElement> contactInputs = contactsBlock.findElements(contactValueInputs);                                    //получить все текстовые инпуты блока
        contactInputs.get(contactInputs.size() - 1).sendKeys(contactValue);                                                 //получить последний элемент в массиве - нужный нам инпут для ввода связи
        logger.info("Введено значение для типа связи: {}",  contactValue);
    }

    public LKskillsPage saveAndContinue() {
        driver.findElement(saveAndContinueButtonLocator).click();
        logger.info("Введенные данные сохранены");
        return new LKskillsPage(driver);
    }

    public WebElement getFname ()  {
        return driver.findElement(fnameLocator);
    }

    public WebElement getFnameLatin ()  {
        return driver.findElement(fnameLatinLocator);
    }

    public WebElement getLname ()  {
        return driver.findElement(lnameLocator);
    }

    public WebElement getLnameLatin ()  {
        return driver.findElement(lnameLatinLocator);
    }

    public WebElement getBirthday ()  {
        return driver.findElement(birthdayLocator);
    }

    public WebElement getCountry ()  {
        return driver.findElement(countryLocator);
    }

    public WebElement getCity ()  {
        return driver.findElement(cityLocator);
    }

    public WebElement getEnglishLevel ()  {
        return driver.findElement(englishLevelLocator);
    }

}
