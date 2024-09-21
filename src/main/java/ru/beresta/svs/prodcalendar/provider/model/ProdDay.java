package ru.beresta.svs.prodcalendar.provider.model;

public class ProdDay implements Comparable<ProdDay> {
    private final DayType dayType;
    private final CalendarDate date;

    public ProdDay(DayType dayType, CalendarDate date) {
        this.dayType = dayType;
        this.date = date;
    }

    public DayType getDayType() {
        return dayType;
    }

    public CalendarDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ProdDay{" +
                "dayType=" + dayType +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(ProdDay other) {
        return this.date.compareTo(other.date);
    }
}
