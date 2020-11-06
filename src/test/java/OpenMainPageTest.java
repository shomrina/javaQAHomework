import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class OpenMainPageTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(OpenMainPageTest.class);

    By contactsBlockLocator = By.cssSelector("div[data-prefix='contact']");

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
        String expectedTitle = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
        driver.get("https://otus.ru/");
        logger.info("Открыта страница отус");
        assertEquals(expectedTitle, driver.getTitle());
        logger.info("Проверка Title страницы завершена с успешным результатом");
    }

    @Test
    public void fillAboutMyself() throws InterruptedException {
        //1. Открыть otus.ru
        driver.get("https://otus.ru/");
        logger.info("Открыта страница отус");
        //2. Авторизоваться на сайте
        auth();
        //3. Войти в личный кабинет
        enterLK();
        //4. В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        By nameLocator = By.id("id_fname");
        WebElement name = (new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(nameLocator)));
        //driver.findElement(By.id("id_fname")).clear(); //имя
        name.clear();
        driver.findElement(By.id("id_fname_latin")).clear();
        driver.findElement(By.id("id_lname")).clear();
        driver.findElement(By.id("id_lname_latin")).clear();
        driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).clear();

        driver.findElement(By.id("id_fname")).sendKeys("Марина");
        driver.findElement(By.id("id_fname_latin")).sendKeys("Marina");
        driver.findElement(By.id("id_lname")).sendKeys("Клипперт");
        driver.findElement(By.id("id_lname_latin")).sendKeys("Klippert");
        driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).sendKeys("06.06.1987");
        //Страна
        if(!driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).getText().contains("Россия"))
        {
            driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).click();
            driver.findElement(By.xpath("//*[contains(text(), 'Россия')]")).click();
        }
        //Город
        if(!driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).getText().contains("Санкт-Петербург"))
        {
            driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).click();
            driver.findElement(By.xpath("//*[contains(text(), 'Санкт-Петербург')]")).click();
        }
        //уровень англ.
        if(!driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).getText().contains("Средний (Intermediate)"))
        {
            driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).click();
            driver.findElement(By.xpath("//*[contains(text(), 'Средний (Intermediate)')]")).click();
        }
        //todo добавить контактную информацию (2 шт)
        //контактная информация. добавление контактов

        //todo убрать этот контактБлок, чтобы два раза его не получать!
        List<WebElement> deleteButtons = driver.findElements(By.cssSelector("div.container__col_12:nth-child(4) > div:nth-child(2) > button:nth-child(1)")); //get all buttons Delete for contact
        logger.info("size contact " + deleteButtons.size());
        for(WebElement deletes : deleteButtons) {           //click DELETE for all contacts
            deletes.click();
        }

        By buttonAddLocator = By.cssSelector("button.lk-cv-block__action:nth-child(6)");
        WebElement buttonAdd = driver.findElement(buttonAddLocator);
        buttonAdd.click();
        logger.info("Нажали 'Добавить'");

        addContact("WhatsApp", "89117750600");

        /*WebElement contactsBlock = driver.findElement(contactsBlockLocator);  //получение блока способ связи, кнопка удалить и добавить (но если ничего не заполнено)

        WebElement contact = contactsBlock.findElement(By.cssSelector("div[data-selected-option-class='lk-cv-block__select-option_selected'] span"));  //кнопка для выбора типа связи
        contact.click(); //open selected options for contacts

        //выбор типа связи
        List<WebElement> contactSelectedList = contactsBlock.findElements(By.cssSelector("div[data-selected-option-class='lk-cv-block__select-option_selected']"));
        WebElement contactSelected = contactSelectedList.get(contactSelectedList.size() - 1);
        WebElement contact1 = (new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(contactSelected.findElement(By.cssSelector("div > div >  button[data-value='whatsapp']"))));
        contact1.click();
        logger.info("Добавлен тип связи WhatsApp");

        //ввод значения для выбранного типа связи
       //List<WebElement> contactInputs = driver.findElements(By.cssSelector("div[data-prefix='contact'] input[type='text']"));  //получить все текстовые инпуты блока
        List<WebElement> contactInputs = contactsBlock.findElements(By.cssSelector("input[type='text']"));  //получить все текстовые инпуты блока
        contactInputs.get(contactInputs.size() - 1).sendKeys("89117750600");  //получить последний элемент в массиве - нужный нам инпут для ввода связи
        logger.info("Введено значение для типа связи");*/

        //ДОБАВЛЕНИЕ ВТОРОГО КОНТАКТА
      //  WebElement buttonAdd2 = (new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(driver.findElement(buttonAddLocator))));
        buttonAdd.click();
        logger.info("Нажали 'Добавить'");
        addContact("Skype", "filled_by_autotest");

        //5. Нажать сохранить
        driver.findElement(By.xpath("//*[contains(text(), 'Сохранить и продолжить')]")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        logger.info("Введенные данные сохранены");
        logger.info("ЗАПУСК ПРОВЕРКИ В НОВОМ БРАУЗЕРЕ");
        //6. Открыть https://otus.ru в “чистом браузере”
       // driver.quit();
        setDown();
        logger.info("Текущие сессия и браузер закрыты");

        /*driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);*/
        setUp();
        logger.info("Драйвер поднят");
        driver.get("https://otus.ru/");
        //7. Авторизоваться на сайте
        auth();
        //8. Войти в личный кабинет
        enterLK();
        logger.info("Выполнен вход на сайт и авторизация в личном кабинете");
        //9. Проверить, что в разделе о себе отображаются указанные ранее данные
     //   Assert.assertEquals("Marina", driver.findElement(By.id("id_fname_latin")).getAttribute("value"));
        WebElement name1 = (new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(nameLocator)));
        Assert.assertEquals("Marina", driver.findElement(By.id("id_fname_latin")).getAttribute("value"));
        Assert.assertEquals("Клипперт", driver.findElement(By.id("id_lname")).getAttribute("value"));
        Assert.assertEquals("Klippert", driver.findElement(By.id("id_lname_latin")).getAttribute("value"));
        Assert.assertEquals("06.06.1987", driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).getAttribute("value"));
        Assert.assertEquals("Россия", driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).getText());
        Assert.assertEquals("Санкт-Петербург", driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).getText());
        Assert.assertEquals("Средний (Intermediate)", driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).getText());
        logger.info("Все проверки пройдены успешно");

        //todo добавить проверку что есть инфа о контактах


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
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonEnterLKlocator)).click();

        By emailLocator = By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailLocator)).sendKeys(login);

        driver.findElement(By.cssSelector(".js-psw-input")).sendKeys(password);
        driver.findElement(By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)")).submit();
        logger.info("Авторизация прошла успешно");
    }

    private void enterLK()
    {
        //todo переписать переход через экшены и клики (не по ссылке)

        By avatarLocator = By.cssSelector(".ic-blog-default-avatar");
        WebElement avatar = (new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(avatarLocator)));

        Actions actions = new Actions(driver);
        actions.moveToElement(avatar).build().perform();

        driver.findElement(By.cssSelector("a[href='/lk/biography/personal/'] > div > b")).click();  //click by MY PROFILE

        logger.info("Перешли в личный кабинет");
    }






    @Ignore
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
    }
}
