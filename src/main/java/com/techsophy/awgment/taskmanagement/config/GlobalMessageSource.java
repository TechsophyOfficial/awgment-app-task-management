package com.techsophy.awgment.taskmanagement.config;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Locale;

import static com.techsophy.awgment.taskmanagement.constants.TaskManagementConstants.*;

@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class GlobalMessageSource
{
    private MessageSource messageSource;

    public String get(String key)
    {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    public String get(String key,String args)
    {
        return messageSource.getMessage(key, new Object[]{args}, LocaleContextHolder.getLocale());
    }

    public String get(String key,Object anyObject)
    {
        return messageSource.getMessage(key, new Object[]{((JSONObject) anyObject).get(ERROR)}, LocaleContextHolder.getLocale());
    }

    public String get(String errorCode, String[] args, Locale locale)
    {
        return messageSource.getMessage(errorCode, args,locale);
    }

    public String get(String key, BigInteger userId)
    {
        return messageSource.getMessage(key, new Object[]{userId}, LocaleContextHolder.getLocale());
    }
}