package com.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aws.dynamodb.repositories.model.FileTransferRequest;
import com.aws.sns.SnsNotificationSender;

@RestController
@RequestMapping(value = "messages")
public class WebController {
	
	@Value("${s3.source.bucket}")
	private String sourceBucket;
	
	@Autowired
	private SnsNotificationSender snsNotificationSender;

	@RequestMapping(value = "/send/{message}", method = RequestMethod.GET)
	public @ResponseBody String sendMessage(@PathVariable String message) {
		FileTransferRequest payload = new FileTransferRequest();
		payload.setFromBucket(sourceBucket);
		payload.setS3Key(message);
		snsNotificationSender.send(payload);
		return "Message payload sent To SNS!";
	}

	@RequestMapping("/")
	public @ResponseBody String index() {
		return "Welcome!";
	}

}
