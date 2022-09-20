package com.techsophy.awgment.taskmanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.awgment.taskmanagement.dto.TaskSchema;
import com.techsophy.awgment.taskmanagement.model.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static com.techsophy.awgment.taskmanagement.constants.TaskManagementConstants.*;

@RequestMapping(BASE_URL + VERSION_V1)
public interface TaskController {
    @PostMapping(TASKS)
    ApiResponse<TaskSchema> createTask(@RequestBody TaskSchema taskSchema) throws IOException;
    @GetMapping(TASKS + TASK_ID)
    ApiResponse<TaskSchema> getTaskById(@PathVariable(ID) String id);

    @GetMapping(TASKS)
    ApiResponse<Stream<TaskSchema>> getAllTasks(
                                                @RequestParam(value = PAGE, required = false,defaultValue = "1") Integer page,
                                                @RequestParam(value = SIZE, required = false) Integer pageSize,
                                                @RequestParam(value = SORT_BY, required = false) String[] sortBy,
                                                @RequestParam(value = FILTER_COLUMN_NAME, required = false) String filterColumn,
                                                @RequestParam(value = FILTER_VALUE, required = false) String filterValue
                                                );

    @DeleteMapping(TASKS + TASK_ID)
    ApiResponse<Void> deleteTaskById(@PathVariable(ID) String id);

    @PatchMapping(TASKS + TASK_ID)
    ApiResponse<TaskSchema> updateTaskFields(@PathVariable(ID) String id,@RequestBody Map<Object,Object> fields) throws JsonProcessingException;

}
