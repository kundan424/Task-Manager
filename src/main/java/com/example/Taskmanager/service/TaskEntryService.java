package com.example.Taskmanager.service;


// service class contains actual business logic and encapsulating the application’s functionality
// Services are typically stateless and are designed to perform specific tasks.
// stateless means : No internal memory of previous interactions

import com.example.Taskmanager.entity.TaskEntry;
import com.example.Taskmanager.entity.User;
import com.example.Taskmanager.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class TaskEntryService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(TaskEntry taskEntry, String username) {
        try {
            User user = userService.findByUsername(username);
            taskEntry.setDate(LocalDateTime.now());
            TaskEntry saved = taskRepository.save(taskEntry);
            user.getTaskEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            log.error("A task with the same title and content already exists!", e);
        }
    }

    public void saveEntry(TaskEntry taskEntry) {
        taskRepository.save(taskEntry);
    }

    public List<TaskEntry> getAll() {
        return taskRepository.findAll();
    }

    public Optional<TaskEntry> findById(ObjectId myId) {
        return taskRepository.findById(myId);
    }

    public void deleteById(ObjectId myId, String username) {
        User user = userService.findByUsername(username);
        user.getTaskEntries().removeIf(x -> x.getId().equals(myId));
        userService.saveUser(user);
        taskRepository.deleteById(myId);
    }

}
