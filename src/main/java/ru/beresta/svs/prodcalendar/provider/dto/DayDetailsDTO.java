package ru.beresta.svs.prodcalendar.provider.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.beresta.svs.prodcalendar.provider.model.CalendarDate;
import ru.beresta.svs.prodcalendar.provider.model.DayType;

import java.time.LocalDate;

public class DayDetailsDTO {
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    public DayDetailsDTO(DayType type, CalendarDate date) {
        this.type = type.name();
        this.date = date.getDate();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}