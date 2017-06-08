package com.aws.sns;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aws.Application;
import com.aws.dynamodb.repositories.model.FileTransferRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SnsNotificationSenderTest {
	
	@Autowired
	private SnsNotificationSender snsNotificationSender;
	
	@Value("${s3.source.bucket}")
	private String bucket;
	
	@Test
	public void test(){
		FileTransferRequest r = new FileTransferRequest(bucket, "app1.txt");
		r.setStatus(null);
		snsNotificationSender.send(r);
	}
}
