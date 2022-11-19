provider "aws" {
  region = var.aws_region
}

resource "aws_dynamodb_table" "basic-dynamodb-table" {
  name           = "Quotes"
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "id"

  attribute {
    name = "id"
    type = "S"
  }



  ttl {
    attribute_name = "TimeToExist"
    enabled        = false
  }


  tags = {
    Name        = "dynamodb-table-quotes"
  }
}


resource "aws_lambda_function" "get_random_quote_lambda" {
  filename = var.file_name
  function_name = var.lambda_name
  role = var.role_arn
  handler = var.handler
  source_code_hash = filebase64sha256(var.file_name)
  runtime = var.lambda_runtime
}

resource "aws_lambda_function_url" "create_function_url" {
  function_name      = aws_lambda_function.get_random_quote_lambda.function_name
  authorization_type = "NONE"
  cors {
    allow_credentials = true
    allow_origins     = ["*"]
    allow_methods     = ["*"]
    allow_headers     = ["date", "keep-alive"]
    expose_headers    = ["keep-alive", "date"]
    max_age           = 86400
  }
}