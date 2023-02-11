package ru.netology.data;

import lombok.Value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataGenerator {

    private DataGenerator() {
    }

    public static int generateMonth() {
        LocalDate date = LocalDate.now();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        int month = date.getMonthValue();
        return month;
    }

    public static int generateYear() {
        LocalDate date = LocalDate.now(); // получаем текущую дату
        int year = date.getYear();
        return year;
    }

    @Value
    public static class CardInfo {
        String cardNumber;
    }
    public static CardInfo getApprovedCardInfo() {
        return new CardInfo("4444444444444441");
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo("4444444444444442");
    }

}
