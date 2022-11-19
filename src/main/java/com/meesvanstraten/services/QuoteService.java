package com.meesvanstraten.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meesvanstraten.configuration.IdGenerator;
import com.meesvanstraten.configuration.Properties;
import com.meesvanstraten.dto.QuoteDto;
import com.meesvanstraten.entities.QuoteEntity;
import com.meesvanstraten.repositories.QuoteRepository;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;


import java.net.URI;
import java.util.Map;


public class QuoteService {
	@Inject
	@ReflectiveAccess
	Properties properties;
	@Inject
	@ReflectiveAccess
	HttpClient httpClient;
	@Inject
	@ReflectiveAccess
	 QuoteRepository quoteRepository;
	@Inject
	@ReflectiveAccess
	ObjectMapper mapper;

	public boolean addQuote(QuoteDto quoteDto){
		QuoteEntity quoteEntity = new QuoteEntity(IdGenerator.generate(),quoteDto.getQuote(),quoteDto.getAuthor());
		return quoteRepository.addQuote(quoteEntity);
	}

	public QuoteDto getQuoteById(String id){
		try{
			Map<String, AttributeValue> valueMap = quoteRepository.getQuoteById(id);
			QuoteDto quoteDto = new QuoteDto(valueMap.get("quote").s(),valueMap.get("author").s());
			return quoteDto;
		}
		catch (NullPointerException e){}

		return null;
	}


	public QuoteDto getRandomQuoteFromApi(){
		URI uri = UriBuilder.of(properties.getQuoteApiUrl().concat("/random.json"))
						.build();

		HttpRequest request = HttpRequest.GET(uri);

		String json = httpClient.toBlocking().retrieve(request);
		try {
			return mapper.readValue(json,QuoteDto.class);
		} catch (JsonProcessingException e) {}

		return null;
	}

}
