package com.aws.sqs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aws.Application;
import com.aws.dynamodb.repositories.model.FileTransferRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SqsMessageSenderTest {
	@Autowired
	private SqsMessageUtil sqsMessageSender;
	
	@Test
	public void test(){
		FileTransferRequest r = new FileTransferRequest("xyz","abc");
		r.setStatus(null);
		r.setRequestId(0);
		sqsMessageSender.send(r);
	}
	

}
