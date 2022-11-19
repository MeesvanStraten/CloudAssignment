package com.meesvanstraten.configuration;

import com.github.ksuid.Ksuid;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

@Requires(classes = Ksuid.class)
@Singleton
public class IdGenerator{

	@NonNull
	public static String generate() {
		return Ksuid.newKsuid().toString();
	}
}