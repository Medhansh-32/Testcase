package com.zomind.testcase.controller;

import com.zomind.testcase.Entity.TestCase;
import com.zomind.testcase.Enums.Priority;
import com.zomind.testcase.Enums.Status;
import com.zomind.testcase.Service.TestCaseService;
import com.zomind.testcase.dto.TestCaseDTO;
import com.zomind.testcase.dto.TestCaseDtoSend;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/testcases")
@RestController
@CrossOrigin("https://testcase-management.onrender.com/")
@Slf4j
public class TestCaseController {

    private final TestCaseService testCaseService;

    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }


    @GetMapping
    public ResponseEntity<Page<TestCaseDtoSend>> getTestCases(@RequestParam(name = "page",defaultValue = "0") int page,
                                                              @RequestParam(name = "size", defaultValue = "5") int size,
                                                              @RequestParam(name = "status", required = false) Status status,
                                                              @RequestParam(name = "priority", required = false) Priority priority) {

        return testCaseService.getTestcases(page, size, status, priority);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TestCase> getById(@PathVariable ObjectId id) {
        return testCaseService.getById(id);

    }

    @PostMapping
    public ResponseEntity<List<TestCase>> createTestCase(@RequestBody List<TestCaseDTO> testCaseDTO)  {
          return  testCaseService.createTestCase(testCaseDTO);
          }

    @PutMapping("/{id}")
    public ResponseEntity<TestCase> updateTestCase(@PathVariable ObjectId id, @RequestBody TestCaseDTO testCaseDTO) {
        return testCaseService.updateTestCase(id, testCaseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTestCase(@PathVariable ObjectId id) {
        return testCaseService.deleteTestCase(id);

    }
}
