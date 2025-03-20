package com.zomind.testcase.Entity;

import com.mongodb.lang.NonNull;
import com.zomind.testcase.Enums.Priority;
import com.zomind.testcase.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Document(collection = "testcase")
@Builder
@AllArgsConstructor
@Data
public class TestCase {

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private Status status;

    @NonNull
    private Priority priority;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
