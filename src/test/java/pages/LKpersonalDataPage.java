package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class LKpersonalDataPage extends AbstractPage {
    private Logger logger = LogManager.getLogger(LKpersonalDataPage.class);

    private By fnameLocator = By.id("id_fname");
    private By contactTypeButtonLocator = By.cssSelector("div[data-selected-option-class='lk-cv-block__select-option_selected'] span");
    private By contactTypeListLocator = By.cssSelector("div[data-selected-option-class='lk-cv-block__select-option_selected']");
    private By contactValueInputs = By.cssSelector("input[type='text']");

    public LKpersonalDataPage(WebDriver driver) {
        super(driver);
        waitVisibilityOfElement(fnameLocator, 5);
    }

    @FindBy(css = "button.lk-cv-block__action:nth-child(6)")
    private WebElement buttonAdd;

    @FindBy(id = "id_fname")
    private WebElement fname;

    @FindBy(id = "id_fname_latin")
    private WebElement fnameLatin;

    @FindBy(id = "id_lname")
    private WebElement lname;

    @FindBy(id = "id_lname_latin")
    private WebElement lnameLatin;

    @FindBy(css = ".input-icon > input:nth-child(1)")
    private WebElement birthday;

    @FindBy(css = ".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")
    private WebElement country;

    @FindBy(css = ".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")
    private WebElement city;

    @FindBy(css = "div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")
    private WebElement englishLevel;

    @FindBy(css = "div.container__col_12:nth-child(4) > div:nth-child(2) > button:nth-child(1)")
    private List<WebElement> deleteButton;                                                                                  //получить все кнопки "Удалить" для контактов

    @FindBy(css = "div[data-prefix='contact']")
    private WebElement contactsBlock;                                                                                       //получение блока способ связи, кнопка удалить и добавить (но если ничего не заполнено)

    @FindBy(xpath = "//*[contains(text(), 'Сохранить и продолжить')]")
    private WebElement saveAndContinueButton;


    public void fillPersonalData(String firstName, String firstNameLatin, String lastName, String lastNameLatin, String dateOfBirthday) {
        clearAndSendKeys(fname, firstName);
        clearAndSendKeys(fnameLatin, firstNameLatin);
        clearAndSendKeys(lname, lastName);
        clearAndSendKeys(lnameLatin, lastNameLatin);
        clearAndSendKeys(birthday, dateOfBirthday);
    }


    public void selectCountry(String selectedCountry) {
        if(!country.getText().contains(selectedCountry))
        {
            country.click();
            driver.findElement(By.xpath("//*[contains(text(), '" + selectedCountry + "')]")).click();
        }
    }

    public void selectCity(String selectedCity) {
        if(!city.getText().contains(selectedCity))
        {
            city.click();
            driver.findElement(By.xpath("//*[contains(text(), '" + selectedCity + "')]")).click();
        }
    }

    public void selectEngLevel(String engLvl) {
        if(!englishLevel.getText().contains(engLvl))
        {
            englishLevel.click();
            driver.findElement(By.xpath("//*[contains(text(), '" + engLvl + "')]")).click();
        }
    }

    public void deleteAllContacts() {
        for(WebElement deletes : deleteButton) {                                                                           //нажать "Удалить" для всех контактов
            deletes.click();
        }
    }

    public void clickButtonAdd() {
        buttonAdd.click();
        logger.info("Нажали 'Добавить'");
    }

    public void addContact(String contactType, String contactValue) {
        WebElement contactButton = contactsBlock.findElement(contactTypeButtonLocator);                                     //кнопка для выбора типа связи
        contactButton.click();                                                                                              //открыть список опций для контактов

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
        saveAndContinueButton.click();
        logger.info("Введенные данные сохранены");
        return new LKskillsPage(driver);
    }

    public WebElement getFname ()  {
        return fname;
    }

    public WebElement getFnameLatin ()  {
        return fnameLatin;
    }

    public WebElement getLname ()  {
        return lname;
    }

    public WebElement getLnameLatin ()  {
        return lnameLatin;
    }

    public WebElement getBirthday ()  {
        return birthday;
    }

    public WebElement getCountry ()  {
        return country;
    }

    public WebElement getCity ()  {
        return city;
    }

    public WebElement getEnglishLevel ()  {
        return englishLevel;
    }

}
