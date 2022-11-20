package com.meesvanstraten;
import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meesvanstraten.dto.QuoteDto;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuoteControllerTests {

    private static MicronautLambdaHandler handler;
    private static Context lambdaContext = new MockLambdaContext();

    @BeforeAll
    public static void setupSpec() {
        try {
            handler = new MicronautLambdaHandler();
        } catch (ContainerInitializationException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void cleanupSpec() {
        handler.getApplicationContext().close();
    }

    @Test
    void testGetRandomQuoteEndpoint() {
        //Arange
        AwsProxyRequest request = new AwsProxyRequest();
        request.setHttpMethod("GET");
        request.setPath("/quote/random");

        //Act

        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);

        //Assert
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void testPostQuoteEndpoint() throws JsonProcessingException {
        //Arange
        ObjectMapper mapper = new ObjectMapper();
        AwsProxyRequest request = new AwsProxyRequest();

        QuoteDto quote = new QuoteDto("My inspirational quote","Mees");

        request.setHttpMethod("POST");
        request.setBody(mapper.writeValueAsString(quote));
        request.setPath("/quote");

        //Act
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);

        //Assert
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void testGetQuoteByAuthorEndpoint() {
        //Arange
        AwsProxyRequest request = new AwsProxyRequest();
        String author = "Mees";

        request.setHttpMethod("GET");
        request.setPath(String.format("/quote/%s",author));

        //Act
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);

        //Assert
        assertEquals(200, response.getStatusCode());
    }
}
