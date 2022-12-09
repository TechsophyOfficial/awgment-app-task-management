package com.techsophy.awgment.taskmanagement.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.awgment.taskmanagement.config.GlobalMessageSource;
import com.techsophy.awgment.taskmanagement.constants.TaskManagementTestConstants;
import com.techsophy.awgment.taskmanagement.dto.PaginationResponsePayload;
import com.techsophy.awgment.taskmanagement.exception.InvalidInputException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.techsophy.awgment.taskmanagement.constants.TaskManagementTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@SpringBootTest
//@ExtendWith({SpringExtension.class})
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class TokenUtilsTest {
    @Mock
    SecurityContext securityContext;
    @Mock
    SecurityContextHolder securityContextHolder;
    @InjectMocks
    TokenUtils tokenUtils;
    @Mock
    GlobalMessageSource globalMessageSource;
    @Mock
    WebClientWrapper mockWebClientWrapper;
    @Mock
    ObjectMapper mockObjectMapper;

    @Order(1)
    @Test
    void getTokenFromIssuerTest() throws Exception {
        InputStream resource = new ClassPathResource(TOKEN_TXT_PATH).getInputStream();
        String result = IOUtils.toString(resource, StandardCharsets.UTF_8);
        String tenant = tokenUtils.getIssuerFromToken(result);
        assertThat(tenant).isEqualTo(TECHSOPHY_PLATFORM);
    }

    @Order(2)
    @Test
    void getPageRequestWithPageTest() {
        PageRequest tenant = tokenUtils.getPageRequest(1, 1, null);
        assertTrue(true);
    }

    @Order(2)
    @Test
    void getPageRequestInvalidInputException()
    {
        Assertions.assertThrows(InvalidInputException.class, () ->
                tokenUtils.getPageRequest(null,null,null));
    }

    @Order(3)
    @Test
    void getTokenFromContext() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Jwt jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        String token = tokenUtils.getTokenFromContext();
        assertThat(token).isNull();
    }

    @Order(4)
    @Test
    void getTokenFromContextException() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        assertThatExceptionOfType(SecurityException.class)
                .isThrownBy(() -> tokenUtils.getTokenFromContext());
    }

    @Order(5)
    @Test
    void getLoggedInUserIdTest() {
//        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        assertThatExceptionOfType(SecurityException.class)
                .isThrownBy(() -> tokenUtils.getLoggedInUserId());
    }

    @Test
    void getIssuerFromContext() {
//        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        assertThatExceptionOfType(SecurityException.class)
                .isThrownBy(() -> tokenUtils.getIssuerFromContext());
    }

    @Test
    void getPaginationResponsePayload() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("abc", "abc");
        list.add(map);
        Pageable pageable = PageRequest.of(1, 1);
        Page page = new PageImpl(list, pageable, 1L);
        PaginationResponsePayload responsePayload = tokenUtils.getPaginationResponsePayload(page, list);
        Assertions.assertNotNull(responsePayload);
    }

    @Order(6)
    @Test
    void getSortByTest() {
        String[] sortByArray = {"name", "id"};
        Sort sort = tokenUtils.getSortBy(sortByArray);
        Assertions.assertNotNull(sort);
    }

    @Test
    void getClientRolesTest() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("user");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "role");
        map.put(TaskManagementTestConstants.CLIENT_ROLES,List.of("abc"));
        String response = RESPONSE;
        WebClient webClient = WebClient.builder().build();
        when(mockWebClientWrapper.createWebClient(any())).thenReturn(webClient);
        when(mockWebClientWrapper.webclientRequest(any(WebClient.class), anyString(), anyString(), eq(null))).thenReturn(response).thenReturn(" ");
        when(this.mockObjectMapper.readValue(anyString(),eq(Map.class))).thenReturn(map);
        when(this.mockObjectMapper.convertValue(any(), eq(List.class))).thenReturn(list);
        InputStream resource = new ClassPathResource(TOKEN_TXT_PATH).getInputStream();
        String result = IOUtils.toString(resource, StandardCharsets.UTF_8);
        List<String> response1 = tokenUtils.getClientRoles(result);
        Assertions.assertNotNull(response1);
        //Assertions.assertThrows(AccessDeniedException.class,()->tokenUtils.getClientRoles("token"));
    }
    @Test
    void getClientRolesTest1() throws IOException {
        String abc = "";
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("user");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "role");
        map.put(TaskManagementTestConstants.CLIENT_ROLES,List.of("abc"));
        String response = RESPONSE;
        WebClient webClient = WebClient.builder().build();
        when(mockWebClientWrapper.createWebClient(any())).thenReturn(webClient);
        when(mockWebClientWrapper.webclientRequest(any(WebClient.class), anyString(), anyString(), eq(null))).thenReturn(abc);
        InputStream resource = new ClassPathResource(TOKEN_TXT_PATH).getInputStream();
        String result = IOUtils.toString(resource, StandardCharsets.UTF_8);
        Assertions.assertThrows(AccessDeniedException.class,()->tokenUtils.getClientRoles(result));
    }
    @Test
    void getClientRolesTest2() throws IOException {
        String abc = "";
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list.add("admin");
        list.add("user");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "role");
        map.put(TaskManagementTestConstants.CLIENT_ROLES,List.of("abc"));
        String response = RESPONSE;
        WebClient webClient = WebClient.builder().build();
        when(mockWebClientWrapper.createWebClient(any())).thenReturn(webClient);
        when(mockWebClientWrapper.webclientRequest(any(WebClient.class), anyString(), anyString(), eq(null))).thenReturn(response).thenReturn(" ");
        when(this.mockObjectMapper.readValue(anyString(),eq(Map.class))).thenReturn(map);
        when(this.mockObjectMapper.convertValue(any(), eq(List.class))).thenReturn(list1);
        InputStream resource = new ClassPathResource(TOKEN_TXT_PATH).getInputStream();
        String result = IOUtils.toString(resource, StandardCharsets.UTF_8);
        tokenUtils.getClientRoles(result);
    }
  }