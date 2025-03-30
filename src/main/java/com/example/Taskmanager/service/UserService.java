package com.example.Taskmanager.service;


// service class contains actual business logic and encapsulating the application’s functionality
// Services are typically stateless and are designed to perform specific tasks.
// stateless means : No internal memory of previous interactions

import com.example.Taskmanager.entity.User;
import com.example.Taskmanager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteById(ObjectId myId) {
        userRepository.deleteById(myId);
    }

}