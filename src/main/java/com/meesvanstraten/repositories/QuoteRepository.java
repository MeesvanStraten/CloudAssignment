package com.meesvanstraten.repositories;

import com.meesvanstraten.configuration.Properties;
import com.meesvanstraten.entities.QuoteEntity;
import io.micronaut.core.annotation.ReflectiveAccess;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;


public class QuoteRepository {
	@Inject
	@ReflectiveAccess
	DynamoDbClient dynamoDbClient;
	@Inject
	@ReflectiveAccess
	Properties properties;



	public Map<String, AttributeValue> getQuoteById(String id){
		try{
			Map<String, AttributeValue> keys = new HashMap<>();
			keys.put("id", AttributeValue.builder().s(id).build());
			GetItemRequest request = GetItemRequest.builder().tableName(properties.dynamoDbTable).key(keys).build();

			return dynamoDbClient.getItem(request).item();
		}
		catch (DynamoDbException e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean addQuote(QuoteEntity quote){
		try{
			HashMap<String,AttributeValue> values = new HashMap<>();

			values.put("id", AttributeValue.builder().s(quote.getId()).build());
			values.put("quote", AttributeValue.builder().s(quote.getQuote()).build());
			values.put("author", AttributeValue.builder().s(quote.getAuthor()).build());

			PutItemRequest req = PutItemRequest.builder().item(values).tableName(properties.dynamoDbTable).build();
			PutItemResponse response =  dynamoDbClient.putItem(req);
			if(response.sdkHttpResponse().isSuccessful()) return true;
		}
		catch (DynamoDbException e ){
		}
		return false;
	}
}
