package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static java.lang.System.*;
import static java.lang.System.getProperty;

public class ServicePage {

    private static String url = getProperty("db.url");
    private static String appURL = getProperty("app.url");
    private static String appPORT = getProperty("app.port");
    private static String userDB = getProperty("app.userDB");
    private static String password = getProperty("app.password");

    List<SelenideElement> input = $$(".input__control");
    SelenideElement cardNumber = input.get(0);
    SelenideElement month = input.get(1);
    SelenideElement year = input.get(2);
    SelenideElement cardOwner = input.get(3);
    SelenideElement cvcCvcNumber = input.get(4);

    public void buyForMoney() {
        open("http://localhost:8090", ServicePage.class);
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
    }

    public void buyOnCredit(){
        open("http://localhost:8090", ServicePage.class);
        $$(".button__content").find(exactText("Купить в кредит")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты")).shouldBe(visible);
    }

    public void verifySuccessMessage() {
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 15000);
    }

    public void verifyErrorMessage() {
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    public void verifyWrongFormatMessage() {
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    public void verifyWrongDateMessage() {
        $$(".input__sub").find(exactText("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void verifyWrongYearMessage() {
        $$(".input__sub").find(exactText("Истёк срок действия карты")).shouldBe(visible);
    }

    public void verifyObligatoryFieldMessage() {
        $$(".input__sub").find(exactText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void setCardNumber(String numberCard) {
        cardNumber.setValue(numberCard);
    }

    public void setMonth(String monthCard) {
        month.setValue(monthCard);
    }

    public void setYear(String yearCard) {
        year.setValue(yearCard);
    }

    public void setCardOwner(String ownerCard) {
        cardOwner.setValue(ownerCard);
    }

    public void setCvcCvcNumber(String cvvNumber) {
        cvcCvcNumber.setValue(cvvNumber);
    }

    public void pushСontinueButton(){
        $$(".button__content").find(exactText("Продолжить")).click();
    }

}