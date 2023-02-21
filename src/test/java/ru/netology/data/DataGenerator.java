package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
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
