variable "aws_region" {
  default = "eu-central-1"
}

variable "role_arn" {
  default = "arn:aws:iam::758181903030:role/lambda_role"
}

variable "lambda_runtime" {
  default = "provided.al2"
}

variable "lambda_name" {
  default = "get_random_quote"
}

variable "handler" {
  default = "io.micronaut.function.aws.proxy.MicronautLambdaHandler"
}

variable "file_name" {
  default = "./target/function.zip"
}

variable "gateway_principal" {
  default = "apigateway.amazonaws.com"
}