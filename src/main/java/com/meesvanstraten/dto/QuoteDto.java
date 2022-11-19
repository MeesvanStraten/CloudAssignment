package com.meesvanstraten.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDto {

	public QuoteDto( @JsonProperty("quote")@NonNull String quote, @JsonProperty("author") @NonNull String author) {
		this.quote = quote;
		this.author = author;
	}

	@NonNull
	@NotBlank
	@JsonAlias("quote")
	private String quote;

	@NonNull
	@NotBlank
	@JsonAlias("author")
	private String author;




	@NonNull
	public String getQuote() {
		return quote;
	}

	@NonNull
	public String getAuthor() {
		return author;
	}

}
