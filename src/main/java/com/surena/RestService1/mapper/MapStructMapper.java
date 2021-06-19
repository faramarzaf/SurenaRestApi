package com.surena.RestService1.mapper;

import com.surena.RestService1.dto.UserGetDto;
import com.surena.RestService1.dto.UserPostDto;
import com.surena.RestService1.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
/**
 * By setting the componentModel attribute to spring,
 * the MapStruct processor will produce a singleton Spring Bean mapper injectable wherever you need.
 */
public interface MapStructMapper {


    UserGetDto userToUserGetDto(User user);

    List<UserGetDto> userToUserGetDto(List<User> allUsers);

    User userPostDtoToUser(UserPostDto userPostDto);

}
