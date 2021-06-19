package com.surena.RestService1.controller;

import com.surena.RestService1.model.User;
import com.surena.RestService1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/save")
    public void save(@Valid @RequestBody User user) {
        service.save(user);
    }

    @PutMapping("/update")
    public void update(@Valid @RequestBody User user) {
        service.update(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @RequestMapping(params = "username", method = RequestMethod.GET)
    public User getByUsername(@RequestParam("username") String username) {
        return service.getByUsername(username);
    }

    @RequestMapping(params = "id", method = RequestMethod.GET)
    public User getById(@RequestParam("id") Long id) {
        return service.getById(id);
    }

    @RequestMapping(params = "id", method = RequestMethod.DELETE)
    public void deleteById(@RequestParam("id") Long id) {
        service.deleteById(id);
    }

    @RequestMapping(params = "username", method = RequestMethod.DELETE)
    public void deleteByUsername(@RequestParam("username") String username) {
        service.deleteByUsername(username);
    }


}
