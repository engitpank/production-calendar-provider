package ru.beresta.svs.prodcalendar.provider.model;

public class DayTypeFactory  {

    public static DayType fromSource(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Day type from source cannot be null");
        }

        switch (type.toUpperCase()) {
            case "GENERAL":
            case "PRE_HOLIDAY":
                return DayType.WORKDAY;
            case "HOLIDAY":
                return DayType.HOLIDAY;
            case "WEEKEND":
                return DayType.WEEKEND;
            default:
                throw new IllegalArgumentException("Unknown day type: " + type);
        }
    }
}