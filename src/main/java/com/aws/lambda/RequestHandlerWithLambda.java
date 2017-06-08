package com.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.lambda.model.Request;

public class RequestHandlerWithLambda implements RequestHandler<Request, String> {
	@Override
	public String handleRequest(Request input, Context context) {
		return input.getFirstName();
	}
}
