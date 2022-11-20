package com.meesvanstraten.repositories;

import com.meesvanstraten.configuration.Properties;
import com.meesvanstraten.entities.QuoteEntity;
import io.micronaut.core.annotation.ReflectiveAccess;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuoteRepository {
	@Inject
	@ReflectiveAccess
	DynamoDbClient dynamoDbClient;
	@Inject
	@ReflectiveAccess
	Properties properties;


	public List<Map<String, AttributeValue>> getQuotesByAuthor(String author){
		try{
			Map<String, AttributeValue> expressionAttributeValues =
							new HashMap<>();
			expressionAttributeValues.put(":author", AttributeValue.builder().s(author).build());


			ScanRequest scanRequest = ScanRequest.builder().tableName(properties.dynamoDbTable)
							.filterExpression("(author = :author)")
							.expressionAttributeValues(expressionAttributeValues)
							.build();


			return dynamoDbClient.scan(scanRequest).items();
		}
		catch (DynamoDbException e){
		}
		return null;
	}


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

			PutItemRequest req = PutItemRequest.builder().item(values).tableName(properties.dynamoDbTable).returnValues(ReturnValue.ALL_OLD).build();
			PutItemResponse response =  dynamoDbClient.putItem(req);
			if(response.sdkHttpResponse().isSuccessful()) return true;
		}
		catch (DynamoDbException e ){
		}
		return false;
	}
}
