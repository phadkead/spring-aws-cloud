package com.aws;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.amazonaws.services.sns.util.Topics;
import com.aws.sns.SnsNotificationSender;
import com.aws.sqs.SqsMessageUtil;

@SpringBootApplication
@ComponentScan
@ImportResource("classpath:aws-config.xml")
@EnableAutoConfiguration
public class Application {

	@Autowired
	private SnsNotificationSender sender;

	@Autowired
	private SqsMessageUtil sqs;
	
	@Value("${sns.topicArn}")
	private String snsTopicArn;
	
	@Value("${sqs.queueUrl}")
	private String sqsQueueUrl;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Subscribes sqs queue to topic on application start up
	 */
	@PostConstruct
	public void init() {
		String status =Topics.subscribeQueue(sender.getAmazonSNS(), sqs.getAmazonSqs(), snsTopicArn,
				sqsQueueUrl);
		System.out.println("Subsribing status of sqs to tpoic " + status);
	}

}