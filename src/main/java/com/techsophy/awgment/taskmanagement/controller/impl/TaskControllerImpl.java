package com.techsophy.awgment.taskmanagement.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.awgment.taskmanagement.config.GlobalMessageSource;
import com.techsophy.awgment.taskmanagement.controller.TaskController;
import com.techsophy.awgment.taskmanagement.dto.TaskSchema;
import com.techsophy.awgment.taskmanagement.model.ApiResponse;
import com.techsophy.awgment.taskmanagement.service.TaskService;
import com.techsophy.awgment.taskmanagement.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import static com.techsophy.awgment.taskmanagement.constants.TaskManagementConstants.*;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class TaskControllerImpl implements TaskController {
    private final GlobalMessageSource globalMessageSource;
    private final TaskService taskService;
    private final TokenUtils tokenUtils;
    @Override
    public ApiResponse<TaskSchema> createTask(TaskSchema taskSchema) throws IOException {
        TaskSchema taskSchema1= taskService.saveTask(taskSchema);
        return new ApiResponse<>(taskSchema1,true, globalMessageSource.get(SAVE_TASK_SUCCESS));
    }

    @Override
    public ApiResponse<TaskSchema> getTaskById(String id) {
        return new ApiResponse<>(taskService.getTaskById(id),true, globalMessageSource.get(GET_TASK_SUCCESS));
    }

    @Override
    public ApiResponse getAllTasks(Integer page, Integer pageSize, String[] sortBy, String filterColumn, String filterValue) {
        if (StringUtils.hasText(filterColumn) && StringUtils.hasText(filterValue))
        {
            if(page==null)
            {
                return new ApiResponse<>(taskService.getAllTasksByFilter(filterColumn,filterValue,tokenUtils.getSortBy(sortBy)), true,
                        globalMessageSource.get(GET_TASK_SUCCESS));
            }
            else
            {
                return new ApiResponse<>(taskService.getAllTasksByFilter(filterColumn, filterValue, tokenUtils.getPageRequest(page, pageSize, sortBy)), true,
                        globalMessageSource.get( GET_TASK_SUCCESS));
            }
        }

        return new ApiResponse<>(taskService.getAllTasks(tokenUtils.getPageRequest(page, pageSize, sortBy)), true, globalMessageSource.get( GET_TASK_SUCCESS));

        }

    @Override
    public ApiResponse<Void> deleteTaskById(String id) {
        taskService.deleteTaskById(id);
        return new ApiResponse<>(null,true, globalMessageSource.get(DELETE_TASK_SUCCESS));
    }

    @Override
    public ApiResponse<TaskSchema> updateTaskFields(String id, Map<Object, Object> fields) throws JsonProcessingException {
        return new ApiResponse<>(taskService.updateTaskFields(id,fields),true, globalMessageSource.get(UPDATE_TASK_SUCCESS));
    }
}
