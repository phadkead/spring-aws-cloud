package com.aws.dynamodb;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.aws.dynamodb.repositories")
public class DynamoDBConfig {

	@Value("${aws.accessKey}")
	private String amazonAWSAccessKey;

	@Value("${aws.secretKey}")
	private String amazonAWSSecretKey;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());
		//amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
		return amazonDynamoDB;
	}

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
	}
	

	/*public void waitForTableAvailable(String tableName) {
		System.out.println(String.format("Waiting for table %s to be available!", tableName));
		DescribeTableRequest request = new DescribeTableRequest();
		request.setTableName(tableName);

		for (int i = 1; i < 10; i++) {
			DescribeTableResult result = amazonDynamoDB().describeTable(request);
			if ("ACTIVE".equals(result.getTable().getTableStatus()))
				return;

			System.out.println(String.format("Table %s not available yet, try %d of %d!", tableName, i, 1));
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				//ignore
			}
		}
	}*/
}