package com.meesvanstraten.configuration;


import io.micronaut.context.annotation.Value;

public class Properties {
	@Value("${application.quoteApiUrl}")
	public String quoteApiUrl;
	@Value("${application.dynamodb.table-name}")
	public String dynamoDbTable;

	public String getQuoteApiUrl(){
		return  quoteApiUrl;
	}
}
