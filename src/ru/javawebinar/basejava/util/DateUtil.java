package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate parse(String date) {
        if ("".equals(date)) {
            return NOW;
        }
        YearMonth ym = YearMonth.parse(date, FORMATTER);
        return of(ym.getYear(), ym.getMonth());
    }

    public static String format(LocalDate ld) {
        return ld == null ? "" : ld.format(FORMATTER);
    }

    public static String toHtml(LocalDate startDate, LocalDate endDate) {
        return format(startDate) + " - " + (endDate.isEqual(NOW) ? "Now" : format(endDate));
    }
}
