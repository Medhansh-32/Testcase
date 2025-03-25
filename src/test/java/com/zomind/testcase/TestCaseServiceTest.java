package com.zomind.testcase;

import com.zomind.testcase.Entity.TestCase;
import com.zomind.testcase.Enums.Priority;
import com.zomind.testcase.Enums.Status;
import com.zomind.testcase.Service.TestCaseService;
import com.zomind.testcase.dto.TestCaseDTO;
import com.zomind.testcase.dto.TestCaseDtoSend;
import com.zomind.testcase.repository.TestCaseRepositories;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestCaseServiceTest {

    @Mock
    private TestCaseRepositories testCaseRepositories;

    @InjectMocks
    private TestCaseService testCaseService;

    private LocalDateTime fixedDateTime;
    private List<TestCase> testCases;
    private List<TestCaseDTO> testCaseDTOs;
    private ObjectId testId;
    private TestCase sampleTestCase;
    private TestCaseDTO sampleTestCaseDTO;

    @BeforeEach
    void setUp() {
        fixedDateTime = LocalDateTime.of(2025, 3, 21, 12, 0);

        testCaseService = Mockito.spy(new TestCaseService(testCaseRepositories, "UTC"));

        lenient().doReturn(fixedDateTime).when(testCaseService).getDateAndTime();

        testId = new ObjectId();

        sampleTestCase = TestCase.builder()
                .id(testId)
                .title("Test Case 1")
                .description("Test Description 1")
                .status(Status.PENDING)
                .priority(Priority.HIGH)
                .createdAt(fixedDateTime)
                .updatedAt(fixedDateTime)
                .build();

        sampleTestCaseDTO = new TestCaseDTO(
                "Test Case 1",
                "Test Description 1",
                Status.PENDING,
                Priority.HIGH
        );

        testCases = new ArrayList<>();
        testCases.add(sampleTestCase);

        testCaseDTOs = new ArrayList<>();
        testCaseDTOs.add(sampleTestCaseDTO);
    }


    @Test
    void createTestCase_NullList_ReturnsBadRequest() {
        ResponseEntity<List<TestCase>> response = testCaseService.createTestCase(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getTestcases_ReturnsListOfTestCases() {
        when(testCaseRepositories.findAll()).thenReturn(testCases);

        ResponseEntity<List<TestCaseDtoSend>> response = testCaseService.getTestcases();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getTestcases_WithPaging_ReturnsPageOfTestCases() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<TestCase> page = new PageImpl<>(testCases, pageable, testCases.size());

        when(testCaseRepositories.findAll(any(Pageable.class))).thenReturn(page);

        ResponseEntity<Page<TestCaseDtoSend>> response = testCaseService.getTestcases(0, 10, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void getTestcases_WithStatusAndPriority_ReturnsFilteredPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<TestCase> page = new PageImpl<>(testCases, pageable, testCases.size());

        doReturn(page).when(testCaseRepositories).findByStatusAndPriority(any(Status.class), any(Priority.class), any(Pageable.class));

        ResponseEntity<Page<TestCaseDtoSend>> response = testCaseService.getTestcases(0, 10, Status.PENDING, Priority.HIGH);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void updateTestCase_NonExistingTestCase_ReturnsNotFound() {
        when(testCaseRepositories.findById(any(ObjectId.class))).thenReturn(Optional.empty());

        ResponseEntity<TestCase> response = testCaseService.updateTestCase(testId, sampleTestCaseDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteTestCase_NonExistingTestCase_ReturnsInternalServerError() {
        doThrow(new RuntimeException("Not Found")).when(testCaseRepositories).deleteById(any(ObjectId.class));

        ResponseEntity<HttpStatus> response = testCaseService.deleteTestCase(testId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}