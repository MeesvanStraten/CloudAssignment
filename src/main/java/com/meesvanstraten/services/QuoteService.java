package com.meesvanstraten.services;

import com.meesvanstraten.configuration.Properties;
import io.micronaut.context.annotation.Bean;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Inject;

import java.net.URI;

@Bean
public class QuoteService {
	@Inject
	Properties properties;

	@Inject
	private HttpClient httpClient;


	public String getRandomQuoteFromApi(){
		URI uri = UriBuilder.of(properties.getQuoteApiUrl().concat("/random.json"))
						.build();
		HttpRequest request = HttpRequest.GET(uri);

		return httpClient.toBlocking().retrieve(request);
	}

}
