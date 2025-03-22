package com.zomind.testcase.Service;

import com.zomind.testcase.Entity.TestCase;
import com.zomind.testcase.Enums.Priority;
import com.zomind.testcase.Enums.Status;
import com.zomind.testcase.dto.TestCaseDTO;

import com.zomind.testcase.repository.TestCaseRepositories;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestCaseService {

    private TestCaseRepositories testCaseRepositories;
    private String zonedId;

    public TestCaseService(TestCaseRepositories testCaseRepositories, @Value("${ZonedId}") String zonedId) {
        this.testCaseRepositories = testCaseRepositories;
        this.zonedId = zonedId;
    }

    public LocalDateTime getDateAndTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(zonedId));
        return zonedDateTime.toLocalDateTime();
    }

    public ResponseEntity<List<TestCase>> createTestCase(List<TestCaseDTO> testCaseDTO){
        if(testCaseDTO==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            LocalDateTime lastUpdated = getDateAndTime();

            List<TestCase> list = testCaseDTO
                    .stream()
                    .map(testCase ->
                            TestCase.builder()
                            .title(testCase.title())
                            .description(testCase.description())
                            .status(testCase.status())
                            .priority(testCase.priority())
                            .createdAt(lastUpdated)
                            .updatedAt(lastUpdated)
                            .build())
                    .collect(Collectors.toList());

            testCaseRepositories.saveAll(list);

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<TestCase>> getTestcases() {
        return new ResponseEntity<>(testCaseRepositories.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Page<TestCase>> getTestcases(int page, int size, Status status, Priority priority) {
        try {
            Pageable pageRequest = PageRequest.of(page, size);
            if (status != null && priority != null) {
                return new ResponseEntity<>(testCaseRepositories.findByStatusAndPriority(status,priority,pageRequest),HttpStatus.OK);
            } else if(status!=null ){
                return new ResponseEntity<>(testCaseRepositories.findByStatus(status, pageRequest), HttpStatus.OK);
            }else if(priority!=null ){
                return new ResponseEntity<>(testCaseRepositories.findByPriority(priority, pageRequest), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(testCaseRepositories.findAll(pageRequest),HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<TestCase> getById(ObjectId id) {
        try {
            Optional<TestCase> testCase = testCaseRepositories.findById(id);
            return testCase.isPresent() ? new ResponseEntity<>(testCase.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<TestCase> updateTestCase(ObjectId id, TestCaseDTO testCaseDTO) {

        try {
            Optional<TestCase> data = testCaseRepositories.findById(id);
            if (data.isPresent()) {
                var testCase = data.get();
                testCase.setId(id);
                testCase.setTitle(testCaseDTO.title());
                testCase.setDescription(testCaseDTO.description());
                testCase.setPriority(testCaseDTO.priority());
                testCase.setStatus(testCaseDTO.status());
                testCase.setUpdatedAt(getDateAndTime());
                testCaseRepositories.save(testCase);
                return new ResponseEntity<>(testCase, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<HttpStatus> deleteTestCase(ObjectId id) {
        try {
            testCaseRepositories.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

