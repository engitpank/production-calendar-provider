package ru.beresta.svs.prodcalendar.provider.service.external.superjob;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class SJHtmlParser {

    public JsonNode SJParseHtml(Document doc) {
        Element scriptElement = doc.selectFirst("script:containsData(window.APP_STATE)");
        if (scriptElement == null) {
            throw new SJParserHtmlException("Script containing APP_STATE was not found");
        }
        String scriptContent = scriptElement.html();
        String json = scriptContent.substring(scriptContent.indexOf("window.APP_STATE =") + 18).trim();

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new SJParserHtmlException("Deserialize json exception: " + e);
        }
    }

    public static class SJParserHtmlException extends RuntimeException {
        public SJParserHtmlException(String message) {
            super(message);
        }
    }
}