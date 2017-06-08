package com.aws.processor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.aws.dynamodb.repositories.FileTransferRepository;
import com.aws.dynamodb.repositories.model.FileTransferRequest;
import com.aws.s3.S3FileUtil;

@Component
public class FileRequestProcessor {

	private static final String FAIL = "fail";

	private static final String SUCCESS = "success";

	@Autowired
	private S3FileUtil s3FileUtil;
	
	@Autowired
	private FileTransferRepository repository;
	
	@Value("${sns.topicArn}")
	private String snsTopicArn;
	
	@Value("${sqs.queue}")
	private String sqsQueueUrl;

	public void process(FileTransferRequest request) {
		try {
			s3FileUtil.copyS3Object(request.getS3Key());
			request.setStatus(SUCCESS);
			repository.save(request);
		} catch (AmazonServiceException | IOException e) {
			request.setStatus(FAIL);
			repository.save(request);
		}
	}
}
