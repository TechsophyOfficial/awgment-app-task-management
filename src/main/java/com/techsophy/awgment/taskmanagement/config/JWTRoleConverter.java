package com.techsophy.awgment.taskmanagement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.awgment.taskmanagement.utils.TokenUtils;
import com.techsophy.awgment.taskmanagement.utils.WebClientWrapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class JWTRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>>
{
    private final WebClientWrapper webClientWrapper;
    private final ObjectMapper objectMapper;
    private final TokenUtils tokenUtils;

    @SneakyThrows
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt)
    {
        List<String> awgmentRolesList =tokenUtils.getClientRoles(jwt.getTokenValue());
        return (awgmentRolesList).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}