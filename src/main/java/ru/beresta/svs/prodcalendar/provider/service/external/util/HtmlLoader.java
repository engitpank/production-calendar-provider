package ru.beresta.svs.prodcalendar.provider.service.external.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlLoader {

    public Document loadHtmlFromUrl(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new HtmlLoaderException(String.format("Loading html from %s was failed: %s", url, e.getMessage()));
        }
    }

    public static class HtmlLoaderException extends RuntimeException {
        public HtmlLoaderException(String message) {
            super(message);
        }
    }
}