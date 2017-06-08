package com.aws.s3;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aws.Application;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class S3FileLoaderTest {
	@Autowired
	private S3FileUtil s3FileLoader;
	
	@Test
	public void shouldgetS3Object() throws IOException{
		assertNotNull(s3FileLoader.getS3Object("app1.txt"));
	}
	
	@Test
	public void shouldcopyS3Object() throws IOException{
		s3FileLoader.copyS3Object("app1.txt");
	}
	
}
