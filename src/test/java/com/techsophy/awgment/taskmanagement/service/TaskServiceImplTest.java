package com.techsophy.awgment.taskmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.awgment.taskmanagement.config.GlobalMessageSource;
import com.techsophy.awgment.taskmanagement.config.LocaleConfig;
import com.techsophy.awgment.taskmanagement.dto.PaginationResponsePayload;
import com.techsophy.awgment.taskmanagement.dto.TaskSchema;
import com.techsophy.awgment.taskmanagement.entity.TaskDefinition;
import com.techsophy.awgment.taskmanagement.repository.TaskRepository;
import com.techsophy.awgment.taskmanagement.service.impl.TaskServiceImpl;
import com.techsophy.awgment.taskmanagement.utils.TokenUtils;
import com.techsophy.awgment.taskmanagement.utils.UserDetails;
import com.techsophy.idgenerator.IdGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import static com.techsophy.awgment.taskmanagement.constants.TaskManagementTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnableWebMvc
@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceImplTest {

    @Mock
    UserDetails mockUserDetails;
    @Mock
    TokenUtils tokenUtils;
    @Mock
    TaskRepository taskRepository;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    IdGeneratorImpl mockIdGenerator;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    LocaleConfig localeConfig;
    @InjectMocks
    TaskServiceImpl mockTaskService;
    List<Map<String, Object>> list = new ArrayList<>();
    List<Map<String, Object>> userList = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();

    @BeforeEach
    public void init() {
        Map<String, Object> map = new HashMap<>();
        map.put(CREATED_BY_ID, NULL);
        map.put(CREATED_BY_NAME, NULL);
        map.put(CREATED_ON, NULL);
        map.put(UPDATED_BY_ID, NULL);
        map.put(UPDATED_BY_NAME, NULL);
        map.put(UPDATED_ON, NULL);
        map.put(ID, BIGINTEGER_ID);
        map.put(USER_NAME, USER_FIRST_NAME);
        map.put(FIRST_NAME, USER_LAST_NAME);
        map.put(LAST_NAME, USER_FIRST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
        userList.add(map);
    }

    @Test
    void updateTaskTest() throws IOException {
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        TaskSchema taskSchema = new TaskSchema();
        taskSchema.setId(BIGINTEGER_ID);
        taskSchema.setAssignee("shankar");
        taskSchema.setDescription("sxp task");
        taskSchema.setOwner("ravi");
        taskSchema.setPriority(2);
        TaskDefinition taskDefinition = new TaskDefinition();
        taskDefinition.setId(BigInteger.valueOf(Long.parseLong(BIGINTEGER_ID)));
        when(mockObjectMapper.convertValue(any(), eq(TaskDefinition.class))).thenReturn(taskDefinition);
        when(mockUserDetails.getUserDetails()).thenReturn(userList);
        when(taskRepository.findById(Long.valueOf(BIGINTEGER_ID))).thenReturn(Optional.of(taskDefinition));
        mockTaskService.saveTask(taskSchema);
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void saveTaskTest() throws IOException {
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        TaskSchema taskSchema = new TaskSchema();
        taskSchema.setAssignee("shankar");
        taskSchema.setDescription("sxp task");
        taskSchema.setOwner("ravi");
        taskSchema.setPriority(2);
        TaskDefinition taskDefinition = new TaskDefinition();
        taskDefinition.setId(BigInteger.valueOf(Long.parseLong(BIGINTEGER_ID)));
        when(mockObjectMapper.convertValue(any(), eq(TaskDefinition.class))).thenReturn(taskDefinition);
        when(mockUserDetails.getUserDetails()).thenReturn(userList);
        when(mockIdGenerator.nextId()).thenReturn(BigInteger.valueOf(Long.parseLong(BIGINTEGER_ID)));
        mockTaskService.saveTask(taskSchema);
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void getTaskByIdTest() {
        TaskDefinition taskDefinition = new TaskDefinition();
        taskDefinition.setAssignee("shankar");
        taskDefinition.setId(BigInteger.valueOf(Long.parseLong(BIGINTEGER_ID)));
        taskDefinition.setDescription("sxp task");
        taskDefinition.setOwner("ravi");
        taskDefinition.setPriority(2);
        TaskSchema taskSchema = new TaskSchema();
        taskSchema.setId(BIGINTEGER_ID);
        taskSchema.setAssignee("shankar");
        taskSchema.setDescription("sxp task");
        taskSchema.setOwner("ravi");
        taskSchema.setPriority(2);
        when(mockObjectMapper.convertValue(any(), eq(TaskSchema.class))).thenReturn(taskSchema);
        when(taskRepository.findById(BigInteger.valueOf(Long.parseLong(BIGINTEGER_ID)))).thenReturn(Optional.of(taskDefinition));
        mockTaskService.getTaskById(BIGINTEGER_ID);
        verify(taskRepository, times(1)).findById((BigInteger) any());
    }

    @Test
    void getAllTasksByPagination() {
        TaskDefinition taskDefinition = new TaskDefinition();
        taskDefinition.setAssignee("shankar");
        taskDefinition.setId(BigInteger.valueOf(Long.parseLong(BIGINTEGER_ID)));
        taskDefinition.setDescription("sxp task");
        taskDefinition.setOwner("ravi");
        taskDefinition.setPriority(2);
        TaskSchema taskSchema = new TaskSchema();
        taskSchema.setId(BIGINTEGER_ID);
        taskSchema.setAssignee("shankar");
        taskSchema.setDescription("sxp task");
        taskSchema.setOwner("ravi");
        taskSchema.setPriority(2);

        Pageable pageable = PageRequest.of(1, 1);
        Page page = new PageImpl(List.of(taskDefinition));
        PaginationResponsePayload paginationResponsePayload = new PaginationResponsePayload(list, 1, 1L, 1, 1, 1);
        when(mockObjectMapper.convertValue(any(), eq(Map.class))).thenReturn(map);
        when(taskRepository.findAll(pageable)).thenReturn(page);

        mockTaskService.getAllTasks(pageable);

        verify(taskRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAllUsersByFilterPagination() {
        TaskDefinition taskDefinition = new TaskDefinition();
        taskDefinition.setAssignee("shankar");
        taskDefinition.setId(BigInteger.valueOf(Long.parseLong(BIGINTEGER_ID)));
        taskDefinition.setDescription("sxp task");
        taskDefinition.setOwner("ravi");
        taskDefinition.setPriority(2);
        TaskSchema taskSchema = new TaskSchema();
        taskSchema.setId(BIGINTEGER_ID);
        taskSchema.setAssignee("shankar");
        taskSchema.setDescription("sxp task");
        taskSchema.setOwner("ravi");
        taskSchema.setPriority(2);
        Pageable pageable = PageRequest.of(1, 1);
        Page page = new PageImpl(List.of(taskDefinition));
        PaginationResponsePayload paginationResponsePayload = new PaginationResponsePayload(list, 1, 1L, 1, 1, 1);
        when(taskRepository.findByFilterColumnAndValue(anyString(), anyString(), any())).thenReturn(page);
        when(mockObjectMapper.convertValue(any(), eq(Map.class))).thenReturn(map);
        PaginationResponsePayload response = mockTaskService.getAllTasksByFilter("abc", "abc", pageable);
        verify(taskRepository, times(1)).findByFilterColumnAndValue(anyString(), anyString(), any());
    }

    @Test
    void getAllTasksListByFilter() {
        TaskDefinition taskDefinition = new TaskDefinition();
        taskDefinition.setAssignee("shankar");
        taskDefinition.setId(BigInteger.valueOf(Long.parseLong(BIGINTEGER_ID)));
        taskDefinition.setDescription("sxp task");
        taskDefinition.setOwner("ravi");
        taskDefinition.setPriority(2);
        TaskSchema taskSchema = new TaskSchema();
        taskSchema.setId(BIGINTEGER_ID);
        taskSchema.setAssignee("shankar");
        taskSchema.setDescription("sxp task");
        taskSchema.setOwner("ravi");
        taskSchema.setPriority(2);

        when(taskRepository.findByFilterColumnAndValue(any(), anyString(), anyString())).thenReturn(List.of(taskDefinition));
        when(mockObjectMapper.convertValue(any(), eq(Map.class))).thenReturn(map);
        List<TaskSchema> response = mockTaskService.getAllTasksByFilter("abc", "abc", Sort.by("abc"));
        verify(taskRepository, times(1)).findByFilterColumnAndValue(any(), anyString(), anyString());
    }
    @Test
    void deleteTaskById(){
        when(taskRepository.existsById(BigInteger.valueOf(1))).thenReturn(true).thenReturn(false);
        doNothing().when(taskRepository).deleteById(BigInteger.valueOf(1));
        mockTaskService.deleteTaskById("1");
        verify(taskRepository,times(1)).existsById(BigInteger.ONE);
    }
}
