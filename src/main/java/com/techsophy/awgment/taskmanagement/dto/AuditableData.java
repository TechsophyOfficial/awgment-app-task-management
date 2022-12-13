package com.techsophy.awgment.taskmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AuditableData {
    private String createdById;
    private Instant createdOn;
    private String updatedById;
    private Instant updatedOn;
}