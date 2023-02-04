package ru.netology.test;

import java.sql.SQLException;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
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
    void shouldBePaidByApprovedCardByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444441");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifySuccessMessage();
    }

    @Test
    @DisplayName("Покупка по неактивной карте в кредит, валидные данные")
    void shouldNotPaidByDeclinedCardByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444442");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyErrorMessage();
    }

    @Test
    @DisplayName("Покупка по карте не из двух предложенных в кредит, валидные данные")
    void shouldNotPaidByUnknownCardByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("7777 7777 7777 7777");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyErrorMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным номером карты в кредит")
    void shouldNotPaidByInvalidCardNumberByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("7777 SSSS 7777 SSSS");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным номером месяца в кредит")
    void shouldNotPaidByInvalidMonthByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444441");
        servicePage.setMonth("18");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongDateMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным номером года в кредит")
    void shouldNotPaidByInvalidYearByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444441");
        servicePage.setMonth("08");
        servicePage.setYear("22");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongYearMessage();
    }


    @Test
    @DisplayName("Покупка по карте c невалидным CVV в кредит")
    void shouldNotPaidByInvalidCVVByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444441");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("77");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка в кредит картой c пустым номером")
    void shouldNotPaidByEmptyCardNumberByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем месяца в кредит")
    void shouldNotPaidByEmptyMonthByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444441");
        servicePage.setMonth("");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем года в кредит")
    void shouldNotPaidEmptyYearFieldByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444441");
        servicePage.setMonth("08");
        servicePage.setYear("");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем владелец в кредит")
    void shouldNotPaidByEmptyCardOwnerByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444441");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем CVV в кредит")
    void shouldNoPayEmptyCVVByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444441");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }
    @Test
    @DisplayName("Покупка по карте c невалидным владельцем в кредит")
    void shouldNotPaidByInvalidCardOwnerByCredit() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444441");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("Иванов Иван 1-ый");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyErrorMessage();
    }

    @Test
    @DisplayName("Покупка по активной карте в кредит, валидные данные, проверка записи данных в БД")
    void shouldPaidByApprovedCardByCreditStatusInDB() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444441");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifySuccessMessage();
        SQLHelper.checkCreditStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Покупка по неактивной карте в кредит, валидные данные, проверка записи данных в БД")
    void shouldPaidByDeclinedCardByCreditStatusInDB() throws SQLException {
        servicePage.buyOnCredit();
        servicePage.setCardNumber("4444444444444442");
        servicePage.setMonth("08");
        servicePage.setYear("23");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
        SQLHelper.checkCreditStatus(Status.DECLINED);
    }
}
