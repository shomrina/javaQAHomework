import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import pages.LKpersonalDataPage;
import pages.LoginPage;
import pages.MainPage;

public class LkPageTest extends BaseTest {

    private Logger logger = LogManager.getLogger(LkPageTest.class);

    @Test
    public void fillAboutMyself() {
        //TEST DATA
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

        //TEST
        //1. Открыть otus.ru
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        //2. Авторизоваться на сайте
        LoginPage loginPage = mainPage.openLoginPage();
        loginPage.auth();
        //3. Войти в личный кабинет
        LKpersonalDataPage lKpersonalDataPage = mainPage.enterLK();
        //4. В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        lKpersonalDataPage.fillPersonalData(firstName, firstNameLatin, lastName, lastNameLatin, dateOfBirthday);
        lKpersonalDataPage.selectCountry(country);                            //Страна
        lKpersonalDataPage.selectCity(city);                                  //Город
        lKpersonalDataPage.selectEngLevel(englishLevel);                      //уровень англ.
        //контактная информация. добавление контактов todo возможно нелучшее решение с отдельной строкой на удаление (выходит из логики шагов теста)
        lKpersonalDataPage.deleteAllContacts();                               //удаление всех контактов (чтобы не делать проверку на их существование)
        lKpersonalDataPage.clickButtonAdd();
        lKpersonalDataPage.addContact(contactType1, contactValue1);
        //добавление второго контакта
        lKpersonalDataPage.clickButtonAdd();
        lKpersonalDataPage.addContact(contactType2, contactValue2);
        //5. Нажать сохранить
        lKpersonalDataPage.saveAndContinue();
        logger.info("ЗАПУСК ПРОВЕРКИ В НОВОМ БРАУЗЕРЕ");
        //6. Открыть https://otus.ru в “чистом браузере”
        setDown();
        setUp();
        MainPage mainPage1 = new MainPage(driver);
        mainPage1.open();
        //7. Авторизоваться на сайте
        LoginPage loginPage1 = mainPage1.openLoginPage();
        loginPage1.auth();
        //8. Войти в личный кабинет
        LKpersonalDataPage lKpersonalDataPage1 = mainPage1.enterLK();
        logger.info("Выполнен вход на сайт и авторизация в личном кабинете");
        //9. Проверить, что в разделе о себе отображаются указанные ранее данные
        Assert.assertEquals(firstName, lKpersonalDataPage1.getAttributeValueElement(lKpersonalDataPage1.getFname()));
        Assert.assertEquals(firstNameLatin, lKpersonalDataPage1.getAttributeValueElement(lKpersonalDataPage1.getFnameLatin()));
        Assert.assertEquals(lastName, lKpersonalDataPage1.getAttributeValueElement(lKpersonalDataPage1.getLname()));
        Assert.assertEquals(lastNameLatin, lKpersonalDataPage1.getAttributeValueElement(lKpersonalDataPage1.getLnameLatin()));
        Assert.assertEquals(dateOfBirthday, lKpersonalDataPage1.getAttributeValueElement(lKpersonalDataPage1.getBirthday()));
        Assert.assertEquals(country, lKpersonalDataPage1.getTextElement(lKpersonalDataPage1.getCountry()));
        Assert.assertEquals(city, lKpersonalDataPage1.getTextElement(lKpersonalDataPage1.getCity()));
        Assert.assertEquals(englishLevel, lKpersonalDataPage1.getTextElement(lKpersonalDataPage1.getEnglishLevel()));
        logger.info("Все проверки пройдены успешно");

        //todo добавить проверку что есть инфа о контактах

    }

}
