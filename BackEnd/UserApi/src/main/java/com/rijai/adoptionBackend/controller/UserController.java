package com.rijai.adoptionBackend.controller;

import com.rijai.adoptionBackend.model.User;
import com.rijai.adoptionBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public List<User> getAllUser()
    {
        return userService.getAllUsers();
    }

    @RequestMapping(value="/users/{id}")
    public User getUser(@PathVariable int id)
    {
        return userService.getUser(id);
    }

    @RequestMapping(value="/add-user", method= RequestMethod.POST)
    public User addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    @RequestMapping(value="/update-user", method=RequestMethod.PUT)
    public User updateUser(@RequestBody User user)
    {
        return userService.updateUser(user);
    }
    @RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id)
    {
        userService.deleteUser(id);
    }
}
