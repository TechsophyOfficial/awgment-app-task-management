package com.techsophy.awgment.taskmanagement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.awgment.taskmanagement.config.GlobalMessageSource;
import com.techsophy.awgment.taskmanagement.constants.TaskManagementConstants;
import com.techsophy.awgment.taskmanagement.dto.PaginationResponsePayload;
import com.techsophy.awgment.taskmanagement.dto.TaskSchema;
import com.techsophy.awgment.taskmanagement.entity.TaskDefinition;
import com.techsophy.awgment.taskmanagement.exception.NoDataFoundException;
import com.techsophy.awgment.taskmanagement.repository.TaskRepository;
import com.techsophy.awgment.taskmanagement.service.TaskService;
import com.techsophy.awgment.taskmanagement.utils.TokenUtils;
import com.techsophy.awgment.taskmanagement.utils.UserDetails;
import com.techsophy.idgenerator.IdGeneratorImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.techsophy.awgment.taskmanagement.constants.ErrorConstants.TASK_NOT_FOUND_EXCEPTION;
import static com.techsophy.awgment.taskmanagement.constants.TaskManagementConstants.*;


@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;
    private final IdGeneratorImpl idGenerator;
    private final GlobalMessageSource globalMessageSource;
    private final UserDetails userDetails;
    private final TokenUtils tokenUtils;

    @Override
    public TaskSchema saveTask(TaskSchema taskSchema) throws JsonProcessingException {
        TaskDefinition taskDefinition = objectMapper.convertValue(taskSchema, TaskDefinition.class);
        Map<String, Object> loggedInUser = userDetails.getUserDetails().get(0);
        String taskId = taskSchema.getId();
        if (taskId == null) {
            taskDefinition.setId(idGenerator.nextId());

            taskDefinition.setCreatedOn(Instant.now());
            taskDefinition.setCreatedById(BigInteger.valueOf(Long.parseLong(loggedInUser.get("id").toString())));
            taskDefinition.setCreatedByName(loggedInUser.get(USER_DEFINITION_FIRST_NAME) + TaskManagementConstants.SPACE + loggedInUser.get(USER_DEFINITION_LAST_NAME));
        } else {
            TaskDefinition taskDefinitionData = this.taskRepository.findById(Long.valueOf(taskId))
                    .orElseThrow(() -> new NoDataFoundException(TASK_NOT_FOUND_EXCEPTION, globalMessageSource.get(TASK_NOT_FOUND_EXCEPTION, taskId)));
            taskDefinition.setId(taskDefinition.getId());
            taskDefinition.setCreatedOn(taskDefinitionData.getCreatedOn());
            taskDefinition.setCreatedById(taskDefinitionData.getCreatedById());
            taskDefinition.setCreatedByName(taskDefinitionData.getCreatedByName());

        }
        taskDefinition.setUpdatedOn(Instant.now());
        taskDefinition.setUpdatedById(BigInteger.valueOf(Long.parseLong(loggedInUser.get(ID).toString())));
        taskDefinition.setUpdatedByName(loggedInUser.get(USER_DEFINITION_FIRST_NAME) + SPACE + loggedInUser.get(USER_DEFINITION_LAST_NAME));
        TaskDefinition taskDefinitionResponse = this.taskRepository.save(taskDefinition);

        return this.objectMapper.convertValue(taskDefinitionResponse, TaskSchema.class);
    }

    @Override
    public TaskSchema getTaskById(String id) {
        TaskDefinition taskDefinition = taskRepository.findById((BigInteger.valueOf(Long.parseLong(id)))).orElseThrow(() -> new NoDataFoundException(TASK_NOT_FOUND_EXCEPTION, globalMessageSource.get(TASK_NOT_FOUND_EXCEPTION, id)));
        return this.objectMapper.convertValue(taskDefinition, TaskSchema.class);
    }


    @Override
    public void deleteTaskById(String taskId) {
        if (taskRepository.existsById(BigInteger.valueOf(Long.parseLong(taskId)))) {
            this.taskRepository.deleteById(BigInteger.valueOf(Long.parseLong(taskId)));
        } else {
            throw new NoDataFoundException(TASK_NOT_FOUND_EXCEPTION, globalMessageSource.get(TASK_NOT_FOUND_EXCEPTION, taskId));
        }
    }

    @Override
    public TaskSchema updateTaskFields(String id, Map<Object, Object> fields) throws JsonProcessingException {
        Map<String, Object> loggedInUser = userDetails.getUserDetails().get(0);
        TaskDefinition taskDefinition = this.taskRepository.findById(BigInteger.valueOf(Long.parseLong(id))).orElseThrow(() -> new NoDataFoundException(TASK_NOT_FOUND_EXCEPTION, globalMessageSource.get(TASK_NOT_FOUND_EXCEPTION, id)));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(TaskDefinition.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, taskDefinition, value);
        });
        taskDefinition.setUpdatedOn(Instant.now());
        taskDefinition.setUpdatedById(BigInteger.valueOf(Long.parseLong(loggedInUser.get(ID).toString())));
        taskDefinition.setUpdatedByName(loggedInUser.get(USER_DEFINITION_FIRST_NAME) + SPACE + loggedInUser.get(USER_DEFINITION_LAST_NAME));
        TaskDefinition taskDefinitionUpdate  = this.taskRepository.save(taskDefinition);

        return objectMapper.convertValue(taskDefinitionUpdate, TaskSchema.class);
    }

    @Override
    public PaginationResponsePayload getAllTasks(Pageable pageable) {


        Page<TaskDefinition> taskDefinitionPage = this.taskRepository.findAll(pageable);

        List<Map<String, Object>> taskSchemaList = taskDefinitionPage.stream().map(this::convertTaskEntityToMap).collect(Collectors.toList());
        return tokenUtils.getPaginationResponsePayload(taskDefinitionPage, taskSchemaList);
    }

    @Override
    public List getAllTasksByFilter(String filterColumn, String filterValue, Sort sort) {
        return this.taskRepository.findByFilterColumnAndValue(sort, filterColumn, filterValue).stream()
                .map(this::convertTaskEntityToMap).collect(Collectors.toList());

    }

    @Override
    public PaginationResponsePayload getAllTasksByFilter(String filterColumn, String filterValue, Pageable pageable) {

        Page<TaskDefinition> userFormDataDefinitionPage = this.taskRepository.findByFilterColumnAndValue(filterColumn, filterValue, pageable);
        List<Map<String, Object>> userFormDataSchemaList = userFormDataDefinitionPage.stream()
                .map(this::convertTaskEntityToMap).collect(Collectors.toList());
        return tokenUtils.getPaginationResponsePayload(userFormDataDefinitionPage, userFormDataSchemaList);

    }

    public Map<String, Object> convertTaskEntityToMap(TaskDefinition taskDefinition) {
        return this.objectMapper.convertValue(taskDefinition, Map.class);
    }
}
