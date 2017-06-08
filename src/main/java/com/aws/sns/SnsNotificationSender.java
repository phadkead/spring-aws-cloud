package com.aws.sns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sns.AmazonSNS;
import com.aws.dynamodb.repositories.model.FileTransferRequest;
@Component
public class SnsNotificationSender {

	private final NotificationMessagingTemplate notificationMessagingTemplate;

	@Value("${sns.topic}")
	private String snsTopic;
	
	private AmazonSNS amazonSNS;

	@Autowired
	public SnsNotificationSender(AmazonSNS amazonSns) {
		this.amazonSNS = amazonSns;
		this.notificationMessagingTemplate = new NotificationMessagingTemplate(amazonSns);
	}

	public void send(FileTransferRequest payload) {
		System.out.println(snsTopic);
		this.notificationMessagingTemplate.convertAndSend(snsTopic, payload);
	}
	
	public NotificationMessagingTemplate getNotificationMessagingTemplate() {
		return notificationMessagingTemplate;
	}
	
	public AmazonSNS getAmazonSNS() {
		return amazonSNS;
	}
}