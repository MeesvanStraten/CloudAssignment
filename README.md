## README

## How to run

### Build
Run ` ./mvnw package -Dpackaging=docker-native -Dmicronaut.runtime=lambda -Pgraalvm -e`to package the application as a GraalVM native image. 
A `.Zip` file will be created in the following directory `./target/function.zip`.

### Deploy
The `main.tf` contains the instructions to provision the following:
- DynamoDB table for storing quotes.
- Lambda function with custom runtime to run the GraalVM application.
- AWS REST API Gateway for invoking Lambda.


Run `Terraform apply` to provision the Lambda and DynamoDB, this will also upload the `.Zip` file created in the previous step to the lambda. 



## Tech used
- Micronaut
  - Micronaut AWS Lambda
- GraalVM
- AWS DynamoDB
- AWS API Gateway
- Terraform