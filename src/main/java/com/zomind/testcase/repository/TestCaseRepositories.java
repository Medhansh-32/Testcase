package com.zomind.testcase.repository;

import com.zomind.testcase.Entity.TestCase;
import com.zomind.testcase.Enums.Priority;
import com.zomind.testcase.Enums.Status;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepositories extends MongoRepository<TestCase, ObjectId> {

     Page<TestCase> findAll(Pageable pageable);
     Page<TestCase> findByStatusAndPriority(Status status, Priority priority,Pageable pageable);

    Page<TestCase> findByStatus(Status status ,Pageable pageRequest);

    Page<TestCase> findByPriority(Priority priority, Pageable pageable);
}
