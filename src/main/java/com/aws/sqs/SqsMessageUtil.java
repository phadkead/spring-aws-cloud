package com.aws.sqs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.aws.dynamodb.repositories.model.FileTransferRequest;
import com.aws.processor.FileRequestProcessor;

@Component

public class SqsMessageUtil {
	private final QueueMessagingTemplate queueMessagingTemplate;
	private AmazonSQSAsync amazonSqs;

	@Autowired
	public SqsMessageUtil(AmazonSQSAsync amazonSqs) {
		this.amazonSqs = amazonSqs;
		queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
	}

	@Value("${sqs.queue}")
	private String sqsQueueName;

	@Autowired
	private FileRequestProcessor fileRequestProcessor;

	public void send(FileTransferRequest request) {
		queueMessagingTemplate.convertAndSend(sqsQueueName, request);
	}

	@SqsListener(value = "${sqs.queue}")
	public void queueListener(String message) throws IOException {
		System.out.println(message);
		FileTransferRequest request = queueMessagingTemplate.receiveAndConvert(message, FileTransferRequest.class);
		fileRequestProcessor.process(request);

	}

	public QueueMessagingTemplate getQueueMessagingTemplate() {
		return queueMessagingTemplate;
	}
	
	public AmazonSQSAsync getAmazonSqs() {
		return amazonSqs;
	}
}
