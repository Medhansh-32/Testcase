package com.zomind.testcase.dto;

import com.zomind.testcase.Enums.Priority;
import com.zomind.testcase.Enums.Status;

public record TestCaseDTO(String title,
                          String description,
                          Status status,
                          Priority priority
                          ) {
}
