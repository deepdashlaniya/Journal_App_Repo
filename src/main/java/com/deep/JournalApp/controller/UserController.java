package com.deep.JournalApp.controller;

import com.deep.JournalApp.Entity.User;
import com.deep.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.addNewUser(user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user){
        Authentication authencation = SecurityContextHolder.getContext().getAuthentication();
        String userName = authencation.getName();

        User userInDb = userService.findByUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.addUser(userInDb);
        }
    }

    @DeleteMapping
    public void deleteUser(){
        Authentication authencation = SecurityContextHolder.getContext().getAuthentication();
        String userName = authencation.getName();

        userService.deleteByUserName(userName);
    }
}
