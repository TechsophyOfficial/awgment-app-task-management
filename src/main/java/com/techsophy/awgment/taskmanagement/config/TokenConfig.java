package com.techsophy.awgment.taskmanagement.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static com.techsophy.awgment.taskmanagement.constants.TaskManagementConstants.AUTHORIZATION;

@Component
public class TokenConfig
{
    private TokenConfig()
    {

    }
    public static String getBearerTokenHeader()
    {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getHeader(AUTHORIZATION);
    }
}
