package com.meesvanstraten.configuration;


import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Configuration;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

public class Properties {
	@Value("${application.quoteApiUrl}")
	public String quoteApiUrl;
	@Value("${application.dynamodb.table-name}")
	public String dynamoDbTable;

	public String getQuoteApiUrl(){
		return  quoteApiUrl;
	}
}
