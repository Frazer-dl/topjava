package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T now, T start, T end) {
        return now.compareTo(start) >= 0 && now.compareTo(end) < 0;
    }

    public static <T extends Comparable<T>> boolean isBetweenClosed(T now, T start, T end) {
        return now.compareTo(start) >= 0 && now.compareTo(end) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

