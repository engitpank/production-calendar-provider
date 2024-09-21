package ru.beresta.svs.prodcalendar.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.beresta.svs.prodcalendar.provider.dto.DayDetailsDTO;
import ru.beresta.svs.prodcalendar.provider.dto.ProdDayResponseDTO;
import ru.beresta.svs.prodcalendar.provider.model.ProdDay;
import ru.beresta.svs.prodcalendar.provider.service.ProdDayService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProdDayController {

    private final ProdDayService prodDayService;

    public ProdDayController(ProdDayService prodDayService) {
        this.prodDayService = prodDayService;
    }

    @GetMapping("/prod-days")
    public ProdDayResponseDTO getProdDays(@RequestParam int year) {
        List<ProdDay> sortedProdDays = prodDayService.getSortedProdDays(year);

        List<DayDetailsDTO> dayDetails = sortedProdDays.stream()
                .map(prodDay -> new DayDetailsDTO(
                        prodDay.getDayType(),
                        prodDay.getDate()))
                .collect(Collectors.toList());

        return new ProdDayResponseDTO(dayDetails.size(), dayDetails);
    }
}