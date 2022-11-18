package com.meesvanstraten.controllers;
import com.meesvanstraten.services.QuoteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

import java.util.Collections;
import java.util.Map;

@Controller
public class QuoteController {
    @Inject
    QuoteService quoteService;

    @Get
    public HttpResponse<String> get() {
        String quote = quoteService.getRandomQuoteFromApi();
        if(quote != null) return HttpResponse.ok().body(quote);

        return HttpResponse.noContent().body("Could not find quote");
    }
}
