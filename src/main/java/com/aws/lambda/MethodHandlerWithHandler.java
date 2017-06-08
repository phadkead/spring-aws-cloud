package com.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;

public class MethodHandlerWithHandler {
	public String handleRequest(String input, Context context) {
		context.getLogger().log("Input is: " + input);
		context.getLogger().log(context.getAwsRequestId());
		return "Hello World - " + input;
	}
}