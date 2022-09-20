package com.techsophy.awgment.taskmanagement.repository;

import com.techsophy.awgment.taskmanagement.entity.TaskDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<TaskDefinition,Long>, TaskDefinitionCustomRepository
{
    Optional<TaskDefinition> findById(BigInteger id);
    boolean existsById(BigInteger id);
    void deleteById(BigInteger id);
}
