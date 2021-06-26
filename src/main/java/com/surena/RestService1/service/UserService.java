package com.surena.RestService1.service;

import com.surena.RestService1.exception.ApiRequestException;
import com.surena.RestService1.model.User;
import com.surena.RestService1.repository.AddressRepository;
import com.surena.RestService1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
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
            user1.setCompany(user.getCompany());
            user1.setAddresses(user.getAddresses());

           /* user1.setEmployees(user.getEmployees());
            user1.setManager(user.getManager());*/

            return userRepository.save(user1);
        }
    }

    @Transactional
    public User update(User user, Long id) {
        User userFromDb = userRepository.getById(id);
        userFromDb.setFirst_name(user.getFirst_name());
        userFromDb.setLast_name(user.getLast_name());
        return userRepository.save(userFromDb);

    }

    @Transactional
    public User updatePassword(User user, Long id, String encodedPassword) {
        User updatedUser = userRepository.getById(id);
        if (encodedPassword.equals(updatedUser.getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(updatedUser);
        } else
            throw new ApiRequestException("Invalid password!");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllByAddressName(String address) {
        return addressRepository.findByAddresses_streetIgnoreCase(address);
    }

    public List<User> getAllByCompanyName(String companyName) {
        return addressRepository.findByCompany_nameIgnoreCase(companyName);
    }

    public User getByUsername(String username) {
        User userByUsername = userRepository.findByUsername(username);
        if (userByUsername == null)
            throw new ApiRequestException("User with username " + username + " not found!");
        else
            return userRepository.findByUsername(username);
    }

    public User getById(Long id) {
        User userById = userRepository.findUserById(id);
        if (userById == null)
            throw new ApiRequestException("User with id " + id + " not found!");
        else
            return userRepository.findUserById(id);
    }


    @Transactional
    public String deleteById(Long id) {
        userRepository.deleteById(id);
        return "User with id " + id + " removed.";
    }


    @Transactional
    public String deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
        return "User with username " + username + " removed.";
    }

    private boolean userExists(String username) {
        return userRepository.existsUserByUsername(username);
    }
}
