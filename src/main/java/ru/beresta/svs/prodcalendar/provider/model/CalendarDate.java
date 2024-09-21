package ru.beresta.svs.prodcalendar.provider.model;

import java.time.LocalDate;

public class CalendarDate implements Comparable<CalendarDate> {

    private final LocalDate date;

    private CalendarDate(LocalDate date) {
        this.date = date;
    }

    public static CalendarDate from(LocalDate date) {
        return new CalendarDate(date);
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "CalendarDate{" +
                "date=" + date +
                '}';
    }

    @Override
    public int compareTo(CalendarDate o) {
        return date.compareTo(o.date);
    }
}