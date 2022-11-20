provider "aws" {
  region = var.aws_region
}

resource "aws_lambda_function" "get_random_quote_lambda" {
  filename = var.file_name
  function_name = var.lambda_name
  role = var.role_arn
  handler = var.handler
  source_code_hash = filebase64sha256(var.file_name)
  runtime = var.lambda_runtime

}

resource "aws_api_gateway_rest_api" "gateway" {
  name        = "QuoteGateway"
  description = "Gateway"
}

resource "aws_api_gateway_resource" "proxy" {
  rest_api_id = "${aws_api_gateway_rest_api.gateway.id}"
  parent_id   = "${aws_api_gateway_rest_api.gateway.root_resource_id}"
  path_part   = "{proxy+}"
}
resource "aws_api_gateway_integration" "lambda" {
  rest_api_id = "${aws_api_gateway_rest_api.gateway.id}"
  resource_id = "${aws_api_gateway_method.proxy.resource_id}"
  http_method = "${aws_api_gateway_method.proxy.http_method}"

  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = "${aws_lambda_function.get_random_quote_lambda.invoke_arn}"
}

resource "aws_api_gateway_method" "proxy" {
  rest_api_id   = "${aws_api_gateway_rest_api.gateway.id}"
  resource_id   = "${aws_api_gateway_resource.proxy.id}"
  http_method   = "ANY"
  authorization = "NONE"
}

resource "aws_api_gateway_deployment" "deployment" {
  rest_api_id = aws_api_gateway_rest_api.gateway.id

  triggers = {
    redeployment = sha1(jsonencode(aws_api_gateway_rest_api.gateway.body))
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_api_gateway_stage" "stage" {
  deployment_id = aws_api_gateway_deployment.deployment.id
  rest_api_id   = aws_api_gateway_rest_api.gateway.id
  stage_name    = "prod"
}

resource "aws_lambda_permission" "apigw" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = "${aws_lambda_function.get_random_quote_lambda.function_name}"
  principal     = var.gateway_principal

  # The /*/* portion grants access from any method on any resource
  # within the API Gateway "REST API".
  source_arn = "${aws_api_gateway_rest_api.gateway.execution_arn}/*/*"
}

resource "aws_dynamodb_table" "basic-dynamodb-table" {
  name           = "Quotes"
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "id"

  attribute {
    name = "id"
    type = "S"
  }


  tags = {
    Name        = "dynamodb-table-quotes"
  }
}





