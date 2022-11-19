package com.meesvanstraten.entities;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

public class QuoteEntity {
	@NonNull
	@NotBlank
	private final String id;

	@NonNull
	@NotBlank
	private final String quote;

	@NonNull
	@NotBlank
	private final String author;

	public QuoteEntity(@NonNull String id, @NonNull String quote, @NonNull String author) {
		this.id = id;
		this.quote = quote;
		this.author = author;
	}

	@NonNull
	public String getId() {
		return id;
	}

	@NonNull
	public String getQuote() {
		return quote;
	}

	@NonNull
	public String getAuthor() {
		return author;
	}
}
