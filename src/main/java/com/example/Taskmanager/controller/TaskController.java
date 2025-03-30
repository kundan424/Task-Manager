package com.example.Taskmanager.controller;

import com.example.Taskmanager.entity.TaskEntry;
import com.example.Taskmanager.service.TaskEntryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskEntryService taskEntryService;

    @GetMapping
    public ResponseEntity<List<TaskEntry>> getAll() {
        List<TaskEntry> all = taskEntryService.getAll();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all , HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskEntry myEntry) {
        log.info("Received request to create a new task: {}", myEntry);
        try {
            myEntry.setDate(LocalDateTime.now());
            taskEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("error in creating new task {}", myEntry.getContent(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getTaskById(@PathVariable ObjectId myId) {
        try {
            Optional<TaskEntry> task = taskEntryService.findById(myId);
            if (task.isPresent()) {
                return new ResponseEntity<>(task.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteTaskById(@PathVariable ObjectId myId) {
        try {
            taskEntryService.deleteById(myId);
            return new ResponseEntity<>(myId , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<TaskEntry> updateById(@PathVariable ObjectId myId, @RequestBody TaskEntry newEntry) {
            TaskEntry old = taskEntryService.findById(myId).orElse(null);
            if (old != null) {
                old.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                taskEntryService.saveEntry(old);
                return  new ResponseEntity<>(old , HttpStatus.OK);
            }
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // above we didn't check the condition newEntry.getTitle() != null because title is already
    // annotated with Notnull

}

