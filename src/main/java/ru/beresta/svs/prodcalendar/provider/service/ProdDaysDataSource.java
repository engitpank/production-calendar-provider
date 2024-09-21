package ru.beresta.svs.prodcalendar.provider.service;

import ru.beresta.svs.prodcalendar.provider.model.ProdDay;

import java.util.List;

public interface ProdDaysDataSource {
    /**
     * Получить список ProductionDay из внешнего источника.
     *
     * @return список объектов ProductionDay
     */
    List<ProdDay> getDayDetails(int year);
}