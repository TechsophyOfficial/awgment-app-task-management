package com.techsophy.awgment.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponsePayload
{
    private List<Map<String,Object>> content;
    private int totalPages;
    private long totalElements;
    private int page;
    private int size;
    private int numberOfElements;
}
