package com.techsophy.awgment.taskmanagement.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NoDataFoundException extends RuntimeException
{
    final String message;
    final String errorcode;
    public NoDataFoundException(String errorCode, String message)
    {
        super(message);
        this.errorcode= errorCode;
        this.message=message;
    }
}