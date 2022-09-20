package com.techsophy.awgment.taskmanagement.repository;


import com.techsophy.awgment.taskmanagement.entity.TaskDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TaskDefinitionCustomRepository {
    List<TaskDefinition> findByFilterColumnAndValue(Sort sort, String filterColumn, String filterValue);
    Page<TaskDefinition> findByFilterColumnAndValue(String filterColumn, String filterValue, Pageable pageable);
}
