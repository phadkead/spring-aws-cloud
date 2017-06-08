package com.aws.s3;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Component
public class S3FileUtil {
	@Autowired
	private AmazonS3Client amazonS3Client;

	@Value("${s3.source.bucket}")
	private String sourceBucket;

	@Value("${s3.destination.bucket}")
	private String destinationBucket;

	public S3ObjectInputStream getS3Object(String s3Key) throws IOException {
		GetObjectRequest getObjectRequest = new GetObjectRequest(sourceBucket, s3Key);
		S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
		S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
		System.out.println(objectInputStream.read());
		return objectInputStream;
	}

	public void copyS3Object(String key) throws IOException, AmazonServiceException {
		try {
			CopyObjectRequest copyObjectRequest = new CopyObjectRequest(sourceBucket, key, destinationBucket, key);
			amazonS3Client.copyObject(copyObjectRequest);
		} catch (AmazonServiceException e) {
			throw e;
		}
	}
}
