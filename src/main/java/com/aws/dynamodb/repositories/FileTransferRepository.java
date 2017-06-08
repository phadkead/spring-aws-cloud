package com.aws.dynamodb.repositories;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.aws.dynamodb.repositories.model.FileTransferRequest;

@EnableScan
public interface FileTransferRepository extends CrudRepository<FileTransferRequest, String> {
	 List<FileTransferRequest> findByRequestId(String requestId);
	 List<FileTransferRequest> findAll();
}
