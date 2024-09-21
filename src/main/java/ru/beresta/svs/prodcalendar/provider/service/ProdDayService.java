package ru.beresta.svs.prodcalendar.provider.service;

import org.springframework.stereotype.Service;
import ru.beresta.svs.prodcalendar.provider.model.ProdDay;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdDayService {
    private final ProdDaysDataSource prodDaysDataSource;

    public ProdDayService(ProdDaysDataSource prodDaysDataSource) {
        this.prodDaysDataSource = prodDaysDataSource;
    }

    public List<ProdDay> getSortedProdDays(int year) {
        List<ProdDay> prodDays = prodDaysDataSource.getDayDetails(year);

        return prodDays.stream()
                .sorted(Comparator.comparing(ProdDay::getDate))
                .collect(Collectors.toList());
    }
}