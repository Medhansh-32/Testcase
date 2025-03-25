package com.zomind.testcase.dto;

import com.zomind.testcase.Enums.Priority;
import com.zomind.testcase.Enums.Status;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TestCaseDtoSend(
        String id,

        String title,


        String description,


        Status status,


        Priority priority,

        LocalDateTime createdAt,

        LocalDateTime updatedAt) {


}
