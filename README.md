## README

## Tech used
- Micronaut
  - Micronaut AWS Lambda
- GraalVM
- AWS DynamoDB
- AWS API Gateway
- Terraform

## How to run
GraalVM is used on the Lambda, but locally you can use any JDK starting from version 8. With Intellij create a new `Micronaut configuration` and select the `src/main/java/com/meesvanstraten/Application.java` as startup class.

### Build
Run ` ./mvnw package -Dpackaging=docker-native -Dmicronaut.runtime=lambda -Pgraalvm -e`to package the application as a GraalVM native image. 
A `.Zip` file will be created in the following directory `./target/function.zip`.

### Deploy
The `main.tf` contains the instructions to provision the following:
- DynamoDB table for storing quotes.
- Lambda function with custom runtime to run the GraalVM application.
- AWS REST API Gateway for invoking Lambda.


  Run `Terraform apply` to provision the Lambda and DynamoDB, this will also upload the `.Zip` file created in the previous step to the lambda.



### Use API
On the following path the API is available: `<GeneratedAPIGatewayUrlHere>/prod`.
The following endpoints are available:

#### `GET /quote/random`
returns a random quote from external API.

#### `GET /quote/{author}`
Returns all quotes by author.

#### `POST /quote`
Post new quote, with as JSON in the body of the request:
`{"quote":"My cool quote","author":"Your name"}`


### Tests
The `src/test` folder contains some tests as well.






