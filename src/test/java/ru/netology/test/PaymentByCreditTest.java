package ru.netology.test;

import java.sql.SQLException;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import ru.netology.data.DataGenerator;
import ru.netology.data.SQLHelper;
import ru.netology.data.Status;
import ru.netology.page.ServicePage;

public class PaymentByCreditTest {
    private ServicePage servicePage;

    @BeforeEach
    void setUpPage() {
        servicePage = new ServicePage();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void clearAll() throws SQLException{
        SQLHelper.clearAllData();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Покупка по активной карте в кредит, валидные данные")
    void shouldBePaidByApprovedCardByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifySuccessMessage();
    }

    @Test
    @DisplayName("Покупка по неактивной карте в кредит, валидные данные")
    void shouldNotPaidByDeclinedCardByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getDeclinedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyErrorMessage();
    }

    @Test
    @DisplayName("Покупка по карте не из двух предложенных в кредит, валидные данные")
    void shouldNotPaidByUnknownCardByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber("7777 7777 7777 7777");
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyErrorMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным номером карты в кредит")
    void shouldNotPaidByInvalidCardNumberByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber("7777 SSSS 7777 SSSS");
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным номером месяца в кредит")
    void shouldNotPaidByInvalidMonthByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth("44");
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongDateMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным номером года в кредит")
    void shouldNotPaidByInvalidYearByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear("99");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongDateMessage();
    }


    @Test
    @DisplayName("Покупка по карте c невалидным CVV в кредит")
    void shouldNotPaidByInvalidCVVByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("77");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка в кредит картой c пустым номером")
    void shouldNotPaidByEmptyCardNumberByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber("");
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем месяца в кредит")
    void shouldNotPaidByEmptyMonthByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth("");
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем года в кредит")
    void shouldNotPaidEmptyYearFieldByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear("");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем владелец в кредит")
    void shouldNotPaidByEmptyCardOwnerByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем CVV в кредит")
    void shouldNoPayEmptyCVVByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }
    @Test
    @DisplayName("Покупка по карте c невалидным владельцем в кредит")
    void shouldNotPaidByInvalidCardOwnerByCredit(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Иванов Иван 1-ый");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyErrorMessage();
    }

    @Test
    @DisplayName("Покупка по активной карте в кредит, валидные данные, проверка записи данных в БД")
    void shouldPaidByApprovedCardByCreditStatusInDB(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifySuccessMessage();
        SQLHelper.checkCreditStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Покупка по неактивной карте в кредит, валидные данные, проверка записи данных в БД")
    void shouldPaidByDeclinedCardByCreditStatusInDB(){
        servicePage.buyOnCredit();
        servicePage.setCardNumber(DataGenerator.getDeclinedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyErrorMessage();
        SQLHelper.checkCreditStatus(Status.DECLINED);
    }
}
