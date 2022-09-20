package com.techsophy.awgment.taskmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.awgment.taskmanagement.config.CustomFilter;
import com.techsophy.awgment.taskmanagement.dto.TaskSchema;
import com.techsophy.awgment.taskmanagement.service.impl.TaskServiceImpl;
import com.techsophy.awgment.taskmanagement.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.techsophy.awgment.taskmanagement.constants.TaskManagementTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {
    private static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtSaveOrUpdate = jwt().authorities(new SimpleGrantedAuthority("yes"));
    private static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRead = jwt().authorities(new SimpleGrantedAuthority("yes"));
    private static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtDelete = jwt().authorities(new SimpleGrantedAuthority("yes"));

    @MockBean
    TokenUtils mockTokenUtils;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    TaskServiceImpl taskServiceImpl;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    CustomFilter customFilter;

    @Mock
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(customFilter)
                .apply(springSecurity())
                .build();
    }

    @Test
    void saveTaskTest() throws Exception {
        ObjectMapper objectMapperTest=new ObjectMapper();
        TaskSchema taskSchema = new TaskSchema();
        taskSchema.setId(BIGINTEGER_ID);
        taskSchema.setAssignee("shankar");
        taskSchema.setDescription("sxp task");
        taskSchema.setOwner("ravi");
        taskSchema.setPriority(2);
        Mockito.when(taskServiceImpl.saveTask(taskSchema)).thenReturn(taskSchema);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL + VERSION_V1 + TASKS)
                .content(objectMapperTest.writeValueAsString(taskSchema))
                .with(jwtSaveOrUpdate)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
    @Test
    void getTaskByIdTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        TaskSchema taskSchema = new TaskSchema();
        taskSchema.setId(BIGINTEGER_ID);
        taskSchema.setAssignee("shankar");
        taskSchema.setDescription("sxp task");
        taskSchema.setOwner("ravi");
        taskSchema.setPriority(2);

        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn("techsophy-platform");
        Mockito.when(taskServiceImpl.getTaskById(BIGINTEGER_ID)).thenReturn(taskSchema);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL+ VERSION_V1+TASKS+"/"+BIGINTEGER_ID)

                .content(objectMapperTest.writeValueAsString(taskSchema))
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

}
