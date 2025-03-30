package com.example.Taskmanager.repository;

import com.example.Taskmanager.entity.TaskEntry;
import com.example.Taskmanager.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, ObjectId> {
    /* this ObjectId type should be same as datatype of id present in entity class */

    User findByUsername(String username);
}
