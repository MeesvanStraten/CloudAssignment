package com.meesvanstraten.configuration;


import io.micronaut.context.annotation.Value;

public class Properties {
	@Value("${application.quoteApiUrl}")
	public String quoteApiUrl;

	public String getQuoteApiUrl(){
		return  quoteApiUrl;
	}
	public void setQuoteApiUrl(String quoteApiUrl){
		this.quoteApiUrl = quoteApiUrl;
	}
}
