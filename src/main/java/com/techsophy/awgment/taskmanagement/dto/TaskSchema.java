package com.techsophy.awgment.taskmanagement.dto;

import lombok.*;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskSchema extends AuditableData implements Serializable {
    private String id;
    private String name;
    private String description;
    private String assignee;
    private String owner;
    private String due;
    private String followUp;
    private long priority;
    private String parentTaskId;
}
