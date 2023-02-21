package ru.netology.test;

import java.sql.SQLException;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import ru.netology.data.DataGenerator;
import ru.netology.data.SQLHelper;
import ru.netology.data.Status;
import ru.netology.page.ServicePage;


public class PaymentByMoneyTest {
    private ServicePage servicePage;

    @BeforeEach
    void setUpService() {
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
    @DisplayName("Покупка по активной карте, валидные данные")
    void shouldBePaidByApprovedCard() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifySuccessMessage();
    }

    @Test
    @DisplayName("Покупка по неактивной карте, валидные данные")
    void shouldNotPaidByDeclinedCard() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getDeclinedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyErrorMessage();
    }

    @Test
    @DisplayName("Покупка по карте не из двух предложенных, валидные данные")
    void shouldNotPaidByUnknownCard() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber("7777 7777 7777 7777");
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyErrorMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным номером карты")
    void shouldNotPaidByInvalidCardNumber() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber("7777 SSSS 7777 SSSS");
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным номером месяца")
    void shouldNotPaidByInvalidMonth() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth("44");
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongDateMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным номером года")
    void shouldNotPaidByInvalidYear() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear("99");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongYearMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным владельцем")
    void shouldNotPaidByInvalidCardOwner() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Иванов Иван 1-ый");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyErrorMessage();
    }

    @Test
    @DisplayName("Покупка по карте c невалидным CVV")
    void shouldNotPaidByInvalidCVV() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("77");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым номером")
    void shouldNotPaidByEmptyCardNumber() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber("");
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым номером месяца")
    void shouldNotPaidByEmptyMonth() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth("");
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым номером года")
    void shouldNotPaidByEmptyYearField() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear("");
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем владелец")
    void shouldNotPaidByEmptyCardOwner() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем CVV")
    void shouldNoPayEmptyCVV() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
    }


    @Test
    @DisplayName("Покупка по активной карте, валидные данные, проверка записи данных в БД")
    void shouldPaidByApprovedCardStatusInDB()  throws SQLException{
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getApprovedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifySuccessMessage();
        SQLHelper.checkPaymentStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Покупка по неактивной карте, валидные данные, проверка записи данных в БД")
    void shouldPaidByDeclinedCardStatusInDB() throws SQLException {
        servicePage.buyForMoney();
        servicePage.setCardNumber(DataGenerator.getDeclinedCardInfo().getCardNumber());
        servicePage.setMonth(DataGenerator.generateMonth());
        servicePage.setYear(DataGenerator.generateYear());
        servicePage.setCardOwner("Ivan Ivanov");
        servicePage.setCvcCvcNumber("999");
        servicePage.pushСontinueButton();
        servicePage.verifyWrongFormatMessage();
        SQLHelper.checkPaymentStatus(Status.DECLINED);
    }
}