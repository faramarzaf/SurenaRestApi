package com.surena.RestService1.service;

import com.surena.RestService1.exception.ApiRequestException;
import com.surena.RestService1.model.User;
import com.surena.RestService1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {


    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        if (userExists(user.getUsername()))
            throw new ApiRequestException("Username has already taken!");
        else {
            User user1 = new User();
            String encodedPassword = passwordEncoder.encode(user.getPassword());

            user1.setUsername(user.getUsername());
            user1.setPassword(encodedPassword);
            user1.setFirst_name(user.getFirst_name());
            user1.setLast_name(user.getLast_name());
            user1.setCreate_date(user.getCreate_date());
            user1.setModified_date(user.getModified_date());

            return repository.save(user1);
        }
    }

    public User update(User user, Long id) {
        User userFromDb = repository.getById(id);
        userFromDb.setFirst_name(user.getFirst_name());
        userFromDb.setLast_name(user.getLast_name());
        return repository.save(userFromDb);

    }

    public User updatePassword(User user, Long id,String password) {
        User updatedUser = repository.getById(id);
        if (user.getPassword().equals(updatedUser.getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(password));
            return repository.save(updatedUser);
        } else
            throw new ApiRequestException("Invalid password!");
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getByUsername(String username) {
        User userByUsername = repository.findByUsername(username);
        if (userByUsername == null)
            throw new ApiRequestException("User with username " + username + " not found!");
        else
            return repository.findByUsername(username);
    }

    public User getById(Long id) {
        User userById = repository.findUserById(id);
        if (userById == null)
            throw new ApiRequestException("User with id " + id + " not found!");
        else
            return repository.findUserById(id);
    }


    public String deleteById(Long id) {
        repository.deleteById(id);
        return "User with id " + id + " removed.";
    }


    public String deleteByUsername(String username) {
        repository.deleteByUsername(username);
        return "User with username " + username + " removed.";
    }

    private boolean userExists(String username) {
        return repository.existsUserByUsername(username);
    }
}
