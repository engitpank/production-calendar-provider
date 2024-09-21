package ru.beresta.svs.prodcalendar.provider.service.external.superjob;

import com.fasterxml.jackson.databind.JsonNode;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.beresta.svs.prodcalendar.provider.model.CalendarDate;
import ru.beresta.svs.prodcalendar.provider.model.DayType;
import ru.beresta.svs.prodcalendar.provider.model.DayTypeFactory;
import ru.beresta.svs.prodcalendar.provider.model.ProdDay;
import ru.beresta.svs.prodcalendar.provider.service.ProdDaysDataSource;
import ru.beresta.svs.prodcalendar.provider.service.external.util.HtmlLoader;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class SJProdDaysDataSourceImpl implements ProdDaysDataSource {

    private static final Logger log = LoggerFactory.getLogger(SJProdDaysDataSourceImpl.class);
    private final String baseUrl;
    private final HtmlLoader htmlLoader;
    private final SJHtmlParser sjHtmlParser;

    public SJProdDaysDataSourceImpl(
            @Value("${provider.source.sj.url}") String superJobBaseUrl,
            HtmlLoader htmlLoader,
            SJHtmlParser sjHtmlParser
    ) {
        this.baseUrl = superJobBaseUrl;
        this.htmlLoader = htmlLoader;
        this.sjHtmlParser = sjHtmlParser;
    }

    @Override
    public List<ProdDay> getDayDetails(int year) {
        List<ProdDay> prodDays = new ArrayList<>();

        Document document = htmlLoader.loadHtmlFromUrl(baseUrl + "/" + year);
        JsonNode rootNode = sjHtmlParser.SJParseHtml(document);

        if (rootNode != null) {
            JsonNode productionCalendarDayNodes = rootNode.path("entities").path("productionCalendarDay");
            processJsonNodesToCalendarDays(prodDays, productionCalendarDayNodes);
        }

        return prodDays;
    }

    private void processJsonNodesToCalendarDays(List<ProdDay> prodDays, JsonNode productionCalendarDayNodes) {
        if (productionCalendarDayNodes.isMissingNode()) return;

        Iterator<Map.Entry<String, JsonNode>> fields = productionCalendarDayNodes.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            JsonNode productionCalendarDayNode = field.getValue();
            processJsonNodeToCalendarDay(prodDays, productionCalendarDayNode);
        }
    }

    private void processJsonNodeToCalendarDay(List<ProdDay> prodDays, JsonNode productionCalendarDayNode) {
        String dateString = productionCalendarDayNode.path("attributes").path("date").asText();
        String sourceType = productionCalendarDayNode.path("relationships").path("productionCalendarDayType")
                .path("data").path("id").asText();
        CalendarDate date = parseDate(dateString);
        try {
            DayType type = DayTypeFactory.fromSource(sourceType);
            prodDays.add(new ProdDay(type, date));
        } catch (IllegalArgumentException e) {
            // Not interrupt, just logging error
            log.debug("Failed to parse day {}", e.getMessage());
        }
    }

    private CalendarDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, formatter);
        return CalendarDate.from(zonedDateTime.toLocalDate());
    }
}