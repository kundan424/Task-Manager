package com.example.Taskmanager.repository;

import com.example.Taskmanager.entity.TaskEntry;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TaskRepository extends MongoRepository<TaskEntry , ObjectId> {

// this string type should be same as datatype of id present in entity class

}
