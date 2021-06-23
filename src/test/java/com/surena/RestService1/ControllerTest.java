package com.surena.RestService1;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surena.RestService1.controller.UserController;
import com.surena.RestService1.dto.UserGetDto;
import com.surena.RestService1.dto.UserPostDto;
import com.surena.RestService1.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserController controller;

    @Test
    public void get_all_users() throws Exception {
        UserGetDto user1 = new UserGetDto(1L, "SamMJ", "0123", "Sam00", "Johns00");
        UserGetDto user2 = new UserGetDto(2L, "SamMJ", "0123", "Sam00", "Johns00");
        UserGetDto user3 = new UserGetDto(3L, "SamMJ", "0123", "Sam00", "Johns00");

        List<UserGetDto> allArrivals = new ArrayList<>();

        allArrivals.add(user1);
        allArrivals.add(user2);
        allArrivals.add(user3);

        given(controller.getAllUsers()).willReturn(allArrivals);

        mvc.perform(get("/api/v1/")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].username", is(user1.getUsername())))
                .andExpect(jsonPath("$[1].username", is(user2.getUsername())))
                .andExpect(jsonPath("$[2].username", is(user3.getUsername())));

        verify(controller, times(1)).getAllUsers();

    }

    @Test
    public void get_user_by_id() throws Exception {
        UserGetDto user1 = new UserGetDto(1L, "SamMJ", "0123", "Sam00", "Johns00");

        given(controller.getById(user1.getId())).willReturn(user1);

        mvc.perform(get("/api/v1/?id=" + user1.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())

                // Although the data type of id is Long but, the returned id in json response is Integer so we cast the user1.getId() to Integer.
                .andExpect(jsonPath("id", equalTo(Integer.valueOf(user1.getId().toString()))));

        verify(controller, times(1)).getById(user1.getId());
    }

    @Test
    public void get_user_by_username() throws Exception {
        UserGetDto user1 = new UserGetDto(1L, "SamMJ", "0123", "Sam00", "Johns00");

        given(controller.getByUsername(user1.getUsername())).willReturn(user1);

        mvc.perform(get("/api/v1/?username=" + user1.getUsername())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username", equalTo(user1.getUsername())));

        verify(controller, times(1)).getByUsername(user1.getUsername());
    }


    @Test
    public void delete_user_by_id() throws Exception {
        when(controller.deleteById(1L))
                .thenReturn("User with id " + 1 + " removed.");

        MvcResult requestResult =
                mvc.perform(delete("/api/v1/")
                        .contentType(APPLICATION_JSON)
                        .param("id", "1"))
                        .andExpect(status().isOk())
                        .andExpect(status().isOk())
                        .andReturn();

        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "User with id " + 1 + " removed.");
    }

    @Test
    public void delete_user_by_username() throws Exception {
        when(controller.deleteByUsername("arash"))
                .thenReturn("User with username arash removed.");

        MvcResult requestResult =
                mvc.perform(delete("/api/v1/deleteByUsername")
                        .contentType(APPLICATION_JSON)
                        .param("username", "arash"))
                        .andExpect(status().isOk())
                        .andReturn();

        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "User with username arash removed.");
    }


    @Test
    public void add_user() throws Exception {
        UserPostDto user1 = new UserPostDto(1L, "SamMJ", "0123", "Sam", "Johns");

        User user = new User(1L, "SamMJ", "0123", "Sam", "Johns");

        when(controller.save(any(UserPostDto.class))).thenReturn(user);
        User userUnderTest = controller.save(user1);

        MvcResult mvcResult =
                mvc.perform(post("/api/v1/save")
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(user)))
                        .andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertThat(userUnderTest.getId()).isEqualTo(1L);
        assertThat(userUnderTest.getUsername()).isEqualTo("SamMJ");
        assertThat(userUnderTest.getPassword()).isEqualTo("0123");
        assertThat(userUnderTest.getFirst_name()).isEqualTo("Sam");
        assertThat(userUnderTest.getLast_name()).isEqualTo("Johns");
        assertEquals(200, status);


    }

    @Test
    public void update_user() throws Exception {
        UserPostDto user1 = new UserPostDto(1L, "SamMJ", "0123", "Sam", "Johns");
        User user = new User(1L, "SamMJ", "0123", "Sam00", "Johns00");

        when(controller.update(any(UserPostDto.class), eq(1L))).thenReturn(user);
        User userUnderTest = controller.update(user1, user1.getId());


        MvcResult mvcResult =
                mvc.perform(put("/api/v1/?id=" + user1.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(user)))
                        .andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertThat(userUnderTest.getId()).isEqualTo(1L);
        assertThat(userUnderTest.getUsername()).isEqualTo("SamMJ");
        assertThat(userUnderTest.getPassword()).isEqualTo("0123");
        assertThat(userUnderTest.getFirst_name()).isEqualTo("Sam00");
        assertThat(userUnderTest.getLast_name()).isEqualTo("Johns00");

        assertEquals(200, status);

    }


    @Test
    public void update_password() throws Exception {
        UserPostDto user1 = new UserPostDto(1L, "SamMJ", "0123", "Sam", "Johns");
        User user = new User(1L, "SamMJ", "0123", "Sam", "Johns");

        when(controller.updatePassword(any(UserPostDto.class), eq(1L),eq("0123"))).thenReturn(user);
        User userUnderTest = controller.updatePassword(user1, user1.getId(),"0123");


        MvcResult mvcResult =
                mvc.perform(put("/api/v1/updatePassword/?id=" + user1.getId())
                        .contentType(APPLICATION_JSON)
                        .header("password",user1.getPassword())
                        .content(mapToJson(user)))
                        .andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertThat(userUnderTest.getId()).isEqualTo(1L);
        assertThat(userUnderTest.getUsername()).isEqualTo("SamMJ");
        assertThat(userUnderTest.getPassword()).isEqualTo("0123");
        assertThat(userUnderTest.getFirst_name()).isEqualTo("Sam");
        assertThat(userUnderTest.getLast_name()).isEqualTo("Johns");

        assertEquals(200, status);

    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
