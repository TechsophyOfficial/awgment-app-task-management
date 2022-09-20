package com.techsophy.awgment.taskmanagement.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tp_task_management")
public class TaskDefinition extends Auditable {
    @NotNull(message = "id can not be null")
    @Id
    private BigInteger id;
    private String name;
    private String description;
    private String assignee;
    private String owner;
    private String due;
    private String followUp;
    private long priority;
    private String parentTaskId;
}
