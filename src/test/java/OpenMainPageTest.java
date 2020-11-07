import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OpenMainPageTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(OpenMainPageTest.class);

    By contactsBlockLocator = By.cssSelector("div[data-prefix='contact']");
    String titleMainPage = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
    String titleLKPage = "Личный кабинет | OTUS";
    String baseURL = "https://otus.ru/";

    By fnameLocator = By.id("id_fname");
    By fnameLatinLocator = By.id("id_fname_latin");
    By lnameLocator = By.id("id_lname");
    By lnameLatinLocator = By.id("id_lname_latin");
    By birthdayLocator = By.cssSelector(".input-icon > input:nth-child(1)");
    By countryLocator = By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)");
    By cityLocator = By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)");
    By englishLevelLocator = By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)");
    By deleteButtonLocator = By.cssSelector("div.container__col_12:nth-child(4) > div:nth-child(2) > button:nth-child(1)");
    By buttonAddLocator = By.cssSelector("button.lk-cv-block__action:nth-child(6)");
    By saveAndContinueButtonLocator = By.xpath("//*[contains(text(), 'Сохранить и продолжить')]");

    @Before
    public void setUp() {
        //ввод опций через пробел внутри кавычек(если опций несколько), например: mvn clean test -Dbrowser="chrome" -Doptions="window-size=1920,1080 incognito" (для линукса кавычки одинарные)
        driver = WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));

        //driver = WebDriverFactory.create("chrome");  //для запуска из ИДЕ (ручная отладка) или строкой выше, но тогда параметры будут браться из пом-файла
        logger.info("Драйвер поднят");
    }

    @Ignore
    @Test
    public void verifyTitleMainPage() {
        driver.get(baseURL);
        logger.info("Открыта страница отус");
        assertEquals(titleMainPage, driver.getTitle());
        logger.info("Проверка Title страницы завершена с успешным результатом");
    }

    @Test
    public void fillAboutMyself() throws InterruptedException {
        String firstName = "Марина";
        String firstNameLatin = "Marina";
        String lastName = "Клипперт";
        String lastNameLatin = "Klippert";
        String dateOfBirthday = "06.06.1987";
        String country = "Россия";
        String city = "Санкт-Петербург";
        String contactType1 = "WhatsApp";
        String contactValue1 = "89117750600";
        String contactType2 = "Skype";
        String contactValue2 = "filled_by_autotest";
        String englishLevel = "Средний (Intermediate)";

        //1. Открыть otus.ru
        driver.get(baseURL);
     //   waitUntilTitleIs(5, titleMainPage);
        logger.info("Открыта страница отус");
        //2. Авторизоваться на сайте
        auth();
        //3. Войти в личный кабинет
        enterLK();
        //4. В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        fillPersonalData(firstName, firstNameLatin, lastName, lastNameLatin, dateOfBirthday);
        selectCountry(country);                            //Страна
        selectCity(city);                                  //Город
        selectEngLevel(englishLevel);                      //уровень англ.
        //контактная информация. добавление контактов
        deleteAllContacts();
        WebElement buttonAdd = driver.findElement(buttonAddLocator);
        buttonAdd.click();
        logger.info("Нажали 'Добавить'");
        addContact(contactType1, contactValue1);
        //добавление второго контакта
      //  WebElement buttonAdd2 = (new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(driver.findElement(buttonAddLocator))));
        buttonAdd.click();
        logger.info("Нажали 'Добавить'");
        addContact(contactType2, contactValue2);
        //5. Нажать сохранить
        saveAndContinue();
        logger.info("Введенные данные сохранены");
        logger.info("ЗАПУСК ПРОВЕРКИ В НОВОМ БРАУЗЕРЕ");
        //6. Открыть https://otus.ru в “чистом браузере”
        setDown();
        logger.info("Текущие сессия и браузер закрыты");
        setUp();
        logger.info("Драйвер поднят");
        driver.get(baseURL);
        //7. Авторизоваться на сайте
        auth();
        //8. Войти в личный кабинет
        enterLK();
        logger.info("Выполнен вход на сайт и авторизация в личном кабинете");
        //9. Проверить, что в разделе о себе отображаются указанные ранее данные
     //   Assert.assertEquals("Marina", driver.findElement(By.id("id_fname_latin")).getAttribute("value"));
        WebElement name1 = waitVisibilityOfElement(fnameLocator, 5); //(new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(fnameLocator)));
        Assert.assertEquals(firstName, name1.getAttribute("value"));
        Assert.assertEquals(firstNameLatin, driver.findElement(fnameLatinLocator).getAttribute("value"));
        Assert.assertEquals(lastName, driver.findElement(lnameLocator).getAttribute("value"));
        Assert.assertEquals(lastNameLatin, driver.findElement(lnameLatinLocator).getAttribute("value"));
        Assert.assertEquals(dateOfBirthday, driver.findElement(birthdayLocator).getAttribute("value"));
        Assert.assertEquals(country, driver.findElement(countryLocator).getText());
        Assert.assertEquals(city, driver.findElement(cityLocator).getText());
        Assert.assertEquals(englishLevel, driver.findElement(englishLevelLocator).getText());
        logger.info("Все проверки пройдены успешно");

        //todo добавить проверку что есть инфа о контактах


    }

    private void fillPersonalData(String firstName, String firstNameLatin, String lastName, String lastNameLatin, String dateOfBirthday) {
        //    waitUntilTitleIs(5, titleLKPage);
        // new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/personal/"));
        WebElement fname = (new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(fnameLocator)));
        // WebElement fname = driver.findElement(fnameLocator);
        fname.clear();
        fname.sendKeys(firstName);

        driver.findElement(fnameLatinLocator).clear();
        driver.findElement(fnameLatinLocator).sendKeys(firstNameLatin);


        driver.findElement(lnameLocator).clear();
        driver.findElement(lnameLocator).sendKeys(lastName);


        driver.findElement(lnameLatinLocator).clear();
        driver.findElement(lnameLatinLocator).sendKeys(lastNameLatin);


        driver.findElement(birthdayLocator).clear();
        driver.findElement(birthdayLocator).sendKeys(dateOfBirthday);
    }

    private void saveAndContinue() {
        driver.findElement(saveAndContinueButtonLocator).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
    }

    private void deleteAllContacts() {
        List<WebElement> deleteButtons = driver.findElements(deleteButtonLocator); //get all buttons Delete for contact
        for(WebElement deletes : deleteButtons) {           //click DELETE for all contacts
            deletes.click();
        }
    }

    private void selectEngLevel(String englishLevel) {
        if(!driver.findElement(englishLevelLocator).getText().contains(englishLevel))
        {
            driver.findElement(englishLevelLocator).click();
            driver.findElement(By.xpath("//*[contains(text(), '" + englishLevel + "')]")).click();
        }
    }

    private void selectCity(String selectedCity) {
        if(!driver.findElement(cityLocator).getText().contains(selectedCity))
        {
            driver.findElement(cityLocator).click();
            driver.findElement(By.xpath("//*[contains(text(), '" + selectedCity + "')]")).click();
        }
    }

    private void selectCountry(String selectedCountry) {
        if(!driver.findElement(countryLocator).getText().contains(selectedCountry))
        {
            driver.findElement(countryLocator).click();
            driver.findElement(By.xpath("//*[contains(text(), '" + selectedCountry + "')]")).click();
        }
    }

    private void waitUntilTitleIs(int timeOutInSeconds, String pageTitle) {
        (new WebDriverWait(driver, timeOutInSeconds)).until(ExpectedConditions.titleIs(pageTitle));
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void addContact(String contactType, String contactValue) {
        WebElement contactsBlock = driver.findElement(contactsBlockLocator);  //получение блока способ связи, кнопка удалить и добавить (но если ничего не заполнено)

        WebElement contact = contactsBlock.findElement(By.cssSelector("div[data-selected-option-class='lk-cv-block__select-option_selected'] span"));  //кнопка для выбора типа связи
        contact.click(); //open selected options for contacts
        //выбор типа связи
        List<WebElement> contactSelectedList = contactsBlock.findElements(By.cssSelector("div[data-selected-option-class='lk-cv-block__select-option_selected']"));
        WebElement contactSelected = contactSelectedList.get(contactSelectedList.size() - 1);
        WebElement contact1 = (new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(contactSelected.findElement(By.cssSelector("div > div >  button[data-value='" + contactType.toLowerCase() + "']"))));
        contact1.click();
        logger.info("Добавлен тип связи " + contactType);

        //ввод значения для выбранного типа связи
        List<WebElement> contactInputs = contactsBlock.findElements(By.cssSelector("input[type='text']"));  //получить все текстовые инпуты блока
        contactInputs.get(contactInputs.size() - 1).sendKeys(contactValue);  //получить последний элемент в массиве - нужный нам инпут для ввода связи
        logger.info("Введено значение для типа связи: " + contactValue);
    }

    private void auth() {
        String login = "milagrous@gmail.com";
        String password = "fJ!ntyy2wRg9Fdh";
        By buttonEnterLKlocator = By.cssSelector("button.header2__auth");
       // waitVisibilityOfElement(buttonEnterLKlocator, 5).click();
        driver.findElement(buttonEnterLKlocator).click();

        By emailLocator = By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)");
        waitVisibilityOfElement(emailLocator, 5).sendKeys(login);

        driver.findElement(By.cssSelector(".js-psw-input")).sendKeys(password);
        driver.findElement(By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)")).submit();
        logger.info("Авторизация прошла успешно");
    }

    private WebElement waitVisibilityOfElement(By locator, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void enterLK() {
        By avatarLocator = By.cssSelector(".ic-blog-default-avatar");
        WebElement avatar = waitVisibilityOfElement(avatarLocator, 5);
        Actions actions = new Actions(driver);
        actions.moveToElement(avatar).build().perform();
        driver.findElement(By.cssSelector("a[href='/lk/biography/personal/'] > div > b")).click();  //click by MY PROFILE
        logger.info("Перешли в личный кабинет");
    }






  /*  @Ignore
    @Test  //моя попытка написать тест, не работает!!! но могут быть полезны какието вещи, потому пока не удаляю
    public void myTestFill(){
        //мои потуги
        //1 открыть отус
      //  driver.get(cfg.url());
        logger.info("Открыта страница отус");
        //2 авторизоваться на сайте
        driver.findElement(By.cssSelector("button.header2__auth")).click(); //кнопка вход
        logger.info("Осуществлен переход на страницу авторизации");
        //3 вход в ЛК
        driver.findElement(By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)")).clear(); //email
        driver.findElement(By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)")).sendKeys("milagrous@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Введите пароль']")).clear();  //pass
        driver.findElement(By.xpath("//input[@placeholder='Введите пароль']")).sendKeys("fJ!ntyy2wRg9Fdh");
        driver.findElement(By.cssSelector("input[placeholder='Введите пароль'")).clear();  //pass
        driver.findElement(By.cssSelector("input[placeholder='Введите пароль'")).sendKeys("fJ!ntyy2wRg9Fdh");
        driver.findElement(By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)")).submit();  //нажатие кнопки войти
        logger.info("Авторизация выполнена успешно: открыт личный кабинет");
        //Thread.sleep(1000);
        //4 Заполнение личных данных и двух контактов в разделе "О себе"
        driver.get("https://otus.ru/lk/biography/personal/"); //поскольку цель теста не проверка выпадающего списка, то используем обычный переход по ссылке, но если надо, то используется action
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/personal/"));
        logger.info("Открыт раздел 'О себе'");
        driver.findElement(By.id("id_fname")).clear(); //имя
        driver.findElement(By.id("id_fname")).sendKeys("Марина");
        driver.findElement(By.id("id_lname")).clear(); //фамилия
        driver.findElement(By.id("id_lname")).sendKeys("Клипперт");
        driver.findElement(By.name("date_of_birth")).clear(); //дата рождения
        driver.findElement(By.name("date_of_birth")).sendKeys("06.06.1987");
        driver.findElement(By.name("date_of_birth")).sendKeys(Keys.ENTER);
        logger.info("Заполнены ФИО и дата рождения");

        driver.findElement(By.name("country")).clear();
        driver.findElement(By.name("country")).findElements(By.cssSelector(".lk-cv-block__select-scroll_country > button")).get(1).click();
        //выбор страны
        // driver.findElement(By.name("country")).clear();
        driver.findElement(By.name(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).click();
        Select dropdownCountry = new Select(driver.findElement(By.className("lk-cv-block__select-scroll_country")));
        dropdownCountry.selectByVisibleText("Россия");
        logger.info("Заполнена Страна");
        //выбор города
        // driver.findElement(By.name("city")).clear();
        driver.findElement(By.name("city")).click();
        Select dropdownCity = new Select(driver.findElement(By.className("lk-cv-block__select-scroll_city")));
        dropdownCity.selectByVisibleText("Санкт-Петербург");
        logger.info("Заполнен Город");
        //выбор уровня англ
        // driver.findElement(By.name("english_level")).clear();
        driver.findElement(By.name("english_level")).click();
        Select dropdownEngLvl = new Select(driver.findElement(By.cssSelector(".lk-cv-block__select-options.lk-cv-block__select-scroll")));
        dropdownEngLvl.selectByVisibleText("Средний (Intermediate)");
        logger.info("Заполнен уровень английского");
        //готовность к переезду
        driver.findElement(By.className("container__col container__col_9")).findElements(By.cssSelector("> label")).get(0).click();
        logger.info("указана готовность к переезду");

        //контактная информация. добавление контактов
        driver.findElement(By.cssSelector("button.lk-cv-block__action:nth-child(6)")).click();
        Select dropdownContact = new Select(driver.findElement(By.name("contact-1-service")));
        dropdownContact.selectByVisibleText("Facebook");
        driver.findElement(By.id("id_contact-1-value")).sendKeys("link1");
        logger.info("Добавлен контакт фейсбук");

        driver.findElement(By.cssSelector("button.lk-cv-block__action:nth-child(6)")).click();
        dropdownContact.selectByVisibleText("OK");
        driver.findElement(By.id("id_contact-3-value")).sendKeys("link2");
        logger.info("Добавлен контакт ОК");

        //5 нажать Сохранить
        driver.findElement(By.cssSelector("button.button_md-4:nth-child(2)")).submit();
    }*/
}
