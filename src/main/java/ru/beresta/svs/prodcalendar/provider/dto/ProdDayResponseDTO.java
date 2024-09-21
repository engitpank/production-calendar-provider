package ru.beresta.svs.prodcalendar.provider.dto;

import java.util.List;

public class ProdDayResponseDTO {
    private int count;
    private List<DayDetailsDTO> days;

    public ProdDayResponseDTO(int count, List<DayDetailsDTO> days) {
        this.count = count;
        this.days = days;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DayDetailsDTO> getDays() {
        return days;
    }

    public void setDays(List<DayDetailsDTO> days) {
        this.days = days;
    }
}