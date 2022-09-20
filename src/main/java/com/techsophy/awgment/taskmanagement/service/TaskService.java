package com.techsophy.awgment.taskmanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.awgment.taskmanagement.dto.PaginationResponsePayload;
import com.techsophy.awgment.taskmanagement.dto.TaskSchema;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface TaskService {


    TaskSchema saveTask(TaskSchema taskSchema) throws JsonProcessingException;

    TaskSchema getTaskById(String id);

  List<TaskSchema>  getAllTasksByFilter(String filterColumn, String filterValue,Sort sort);
  PaginationResponsePayload getAllTasksByFilter(String filterColumn, String filterValue, Pageable pageable);
    PaginationResponsePayload getAllTasks(Pageable pageable);
    void deleteTaskById(String userId);
   TaskSchema updateTaskFields(String id, Map<Object,Object> fields) throws JsonProcessingException;

}
