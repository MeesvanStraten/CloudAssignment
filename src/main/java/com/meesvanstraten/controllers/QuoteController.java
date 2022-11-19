package com.meesvanstraten.controllers;
import com.meesvanstraten.dto.QuoteDto;
import com.meesvanstraten.services.QuoteService;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;


@Controller
public class QuoteController {
    @Inject
    @ReflectiveAccess
    QuoteService quoteService;


    @Get
    public HttpResponse get(String id) {
        QuoteDto quote = quoteService.getQuoteById(id);
        if( quote != null) return HttpResponse.ok().body(quote);

        return HttpResponse.notFound().body("Could not find quote by this id");
    }

    @Get("/random")
    public HttpResponse getRandomFromExternalApi() {
        QuoteDto quote = quoteService.getRandomQuoteFromApi();
        if( quote != null) return HttpResponse.ok().body(quote);

        return HttpResponse.notFound().body("Could not get quote from external api");
    }

    @Post
    public HttpResponse getRandomFromExternalApi(QuoteDto quoteDto) {
        if( quoteService.addQuote(quoteDto)) return HttpResponse.created("Created quote");

        return HttpResponse.badRequest().body("Could not create quote");
    }
}
