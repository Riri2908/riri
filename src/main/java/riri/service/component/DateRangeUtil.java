package riri.service.component;

import java.time.*;

public class DateRangeUtil {

    public static LocalDate start(LocalDate date, Period period) {
        return switch (period) {
            case DAY -> date;
            case WEEK -> date.with(DayOfWeek.MONDAY);
            case MONTH -> date.withDayOfMonth(1);
            case YEAR -> date.withDayOfYear(1);
        };
    }

    public static LocalDate end(LocalDate date, Period period) {
        return switch (period) {
            case DAY -> date;
            case WEEK -> date.with(DayOfWeek.MONDAY).plusDays(6);
            case MONTH -> date.withDayOfMonth(date.lengthOfMonth());
            case YEAR -> date.withDayOfYear(date.lengthOfYear());
        };
    }

    public static boolean inRange(LocalDate target, LocalDate start, LocalDate end) {
        return target != null && !target.isBefore(start) && !target.isAfter(end);
    }
}