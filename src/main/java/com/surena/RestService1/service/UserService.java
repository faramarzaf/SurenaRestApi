package com.surena.RestService1.service;

import com.surena.RestService1.exception.ApiRequestException;
import com.surena.RestService1.model.User;
import com.surena.RestService1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;

    public void save(User user) {
        if (userExists(user.getUsername()))
            throw new ApiRequestException("Username has already taken!");
        else
            repository.save(user);
    }

    public void update(User user) {
        User updatedUser = repository.getOne(user.getId());
        updatedUser.setFirst_name(user.getFirst_name());
        updatedUser.setLast_name(user.getLast_name());
        repository.save(updatedUser);
    }


    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User getById(Long id) {
        return repository.findUserById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteUserById(id);
    }

    @Transactional
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

    private boolean userExists(String username) {
        return repository.existsUserByUsername(username);
    }
}
