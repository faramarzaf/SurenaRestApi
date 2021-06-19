package com.surena.RestService1.controller;

import com.surena.RestService1.dto.UserGetDto;
import com.surena.RestService1.dto.UserPostDto;
import com.surena.RestService1.mapper.MapStructMapper;
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

    @Autowired
    private MapStructMapper mapstructMapper;

    @PostMapping("/save")
    public void save(@Valid @RequestBody UserPostDto userPostDto) {
        service.save(mapstructMapper.userPostDtoToUser(userPostDto));
    }

    @PutMapping("/update")
    public void update(@Valid @RequestBody UserPostDto userPostDto) {
        service.update(mapstructMapper.userPostDtoToUser(userPostDto));
    }

    @PutMapping("/updatePassword")
    public void updatePassword(@Valid @RequestBody UserPostDto userPostDto) {
        service.updatePassword(mapstructMapper.userPostDtoToUser(userPostDto));
    }

    @GetMapping
    public List<UserGetDto> getAllUsers() {
        return mapstructMapper.userToUserGetDto(service.getAllUsers());
    }

    @RequestMapping(params = "username", method = RequestMethod.GET)
    public UserGetDto getByUsername(@RequestParam("username") String username) {
        return mapstructMapper.userToUserGetDto(service.getByUsername(username));
    }

    @RequestMapping(params = "id", method = RequestMethod.GET)
    public UserGetDto getById(@RequestParam("id") Long id) {
        return mapstructMapper.userToUserGetDto(service.getById(id));
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
