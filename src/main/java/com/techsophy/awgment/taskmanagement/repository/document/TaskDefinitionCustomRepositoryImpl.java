package com.techsophy.awgment.taskmanagement.repository.document;

import com.techsophy.awgment.taskmanagement.constants.TaskManagementConstants;
import com.techsophy.awgment.taskmanagement.entity.TaskDefinition;
import com.techsophy.awgment.taskmanagement.repository.TaskDefinitionCustomRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
@AllArgsConstructor
public class TaskDefinitionCustomRepositoryImpl implements TaskDefinitionCustomRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<TaskDefinition> findByFilterColumnAndValue(Sort sort, String filterColumn, String filterValue) {
        Query query=new Query();
        Collation collation = Collation.of(TaskManagementConstants.COLLATION_EN).strength(1);
        query.addCriteria(where(filterColumn).is(filterValue)).with(sort).collation(collation);
        query.with(Sort.by(Sort.Direction.ASC, "taskName"));
        return mongoTemplate.find(query,TaskDefinition.class);
    }
    @Override
    public Page<TaskDefinition> findByFilterColumnAndValue(String filterColumn, String filterValue, Pageable pageable)
    {
        Query query = new Query();
        Query countQuery = new Query();
        Collation collation = Collation.of(TaskManagementConstants.COLLATION_EN).strength(1);
        query.addCriteria(where(filterColumn).is(filterValue)).with(pageable).collation(collation);
        List<TaskDefinition> taskDefinitions = mongoTemplate.find(query,TaskDefinition.class);

        query.with(Sort.by(Sort.Direction.ASC, "taskName"));
        countQuery.addCriteria(where(filterColumn).is(filterValue));

        long count=mongoTemplate.count(countQuery,TaskDefinition.class);
        return new PageImpl<>(taskDefinitions, pageable,count );


    }
}
