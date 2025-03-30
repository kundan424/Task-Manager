package com.example.Taskmanager.controller;

import com.example.Taskmanager.entity.TaskEntry;
import com.example.Taskmanager.entity.User;
import com.example.Taskmanager.service.TaskEntryService;
import com.example.Taskmanager.service.UserService;
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
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
         try {
             userService.saveUser(user);
             return  new ResponseEntity<>(user , HttpStatus.CREATED);
         }catch (Exception e){
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateByUsername(@RequestBody User user, @PathVariable String username) {
        User userInDb = userService.findByUsername(username);
        if (userInDb != null) {
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

