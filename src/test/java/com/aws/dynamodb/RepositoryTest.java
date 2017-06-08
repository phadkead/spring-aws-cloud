package com.aws.dynamodb;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.appstream.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.aws.Application;
import com.aws.dynamodb.repositories.FileTransferRepository;
import com.aws.dynamodb.repositories.model.FileTransferRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RepositoryTest {

	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Autowired
	private FileTransferRepository repository;

	@Before
	public void setup() throws Exception {
		dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
		try {
			DeleteTableRequest deleteTableRequest = dynamoDBMapper
					.generateDeleteTableRequest(FileTransferRequest.class);
			amazonDynamoDB.deleteTable(deleteTableRequest);
		} catch (ResourceNotFoundException | ResourceInUseException e) {
			e.printStackTrace();
		}
		try {
			CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(FileTransferRequest.class);
			tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
			// TODO retry

		} catch (ResourceNotFoundException | ResourceInUseException e) {
			e.printStackTrace();
		}
		dynamoDBMapper.batchDelete((List<FileTransferRequest>) repository.findAll());
	}

	@Test
	public void sampleTestCase() {
		//given
		FileTransferRequest request = new FileTransferRequest("fromBucket", "s3Key");
		//when
		repository.save(request);
		List<FileTransferRequest> result = (List<FileTransferRequest>) repository.findAll();
		//then
		assertTrue("Not empty", result.size() > 0);
		assertTrue("Contains item with s3Key ", result.get(0).getS3Key().equals("s3Key"));
	}

}