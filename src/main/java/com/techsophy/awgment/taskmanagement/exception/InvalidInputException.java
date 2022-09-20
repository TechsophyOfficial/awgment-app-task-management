package com.techsophy.awgment.taskmanagement.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvalidInputException extends RuntimeException
{
    final String errorcode;
    final String message;
    public InvalidInputException(String errorCode, String message)
    {
        super(errorCode);
        this.errorcode = errorCode;
        this.message=message;
    }
}