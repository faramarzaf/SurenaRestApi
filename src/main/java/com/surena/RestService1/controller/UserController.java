package com.surena.RestService1.controller;

import com.surena.RestService1.dto.UserGetDto;
import com.surena.RestService1.dto.UserPostDto;
import com.surena.RestService1.mapper.MapStructMapper;
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

    @Autowired
    private MapStructMapper mapstructMapper;

    @PostMapping("/save")
    public User save(@Valid @RequestBody UserPostDto userPostDto) {
        User user = mapstructMapper.userPostDtoToUser(userPostDto);
        return service.save(user);
    }

    @RequestMapping(params = "id", method = RequestMethod.PUT)
    public User update(@Valid @RequestBody UserPostDto userPostDto,
                       @RequestParam("id") Long id) {

        return service.update(mapstructMapper.userPostDtoToUser(userPostDto), id);
    }

    @RequestMapping(path = "/updatePassword", params = "id", method = RequestMethod.PUT)
    public User updatePassword(@Valid @RequestBody UserPostDto userPostDto,
                               @RequestParam("id") Long id) {

        return service.updatePassword(mapstructMapper.userPostDtoToUser(userPostDto), id);
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
    public String deleteById(@RequestParam("id") Long id) {
        return service.deleteById(id);
    }

    @RequestMapping(params = "username", method = RequestMethod.DELETE)
    public String deleteByUsername(@RequestParam("username") String username) {
        return service.deleteByUsername(username);
    }


}
