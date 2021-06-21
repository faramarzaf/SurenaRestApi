package com.surena.RestService1;


import com.surena.RestService1.controller.UserController;
import com.surena.RestService1.dto.UserGetDto;
import com.surena.RestService1.dto.UserPostDto;
import com.surena.RestService1.model.User;
import com.surena.RestService1.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserController controller;

    @Autowired
    private UserService service;

    @Test
    public void get_all_users() throws Exception {
        UserGetDto user1 = new UserGetDto(1L, "SamMJ", "0123", "0124", "Sam00", "Johns00");
        UserGetDto user2 = new UserGetDto(2L, "SamMJ", "0123", "0124", "Sam00", "Johns00");
        UserGetDto user3 = new UserGetDto(3L, "SamMJ", "0123", "0124", "Sam00", "Johns00");

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
        UserGetDto user1 = new UserGetDto(1L, "SamMJ", "0123", "0124", "Sam00", "Johns00");

        given(controller.getById(user1.getId())).willReturn(user1);

        mvc.perform(get("/api/v1/?id=" + user1.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())

                // Although the data type of id is Long but, the returned id in json response is Integer so we cast the user1.getId() to Integer.
                .andExpect(jsonPath("id", equalTo(Integer.valueOf(user1.getId().toString()))));

        verify(controller, times(1)).getById(user1.getId());
    }

    @Test
    public void testDeleteExample() throws Exception {
/*        Mockito.when(service.deleteByUsername(ArgumentMatchers.anyString())).thenReturn("Student is deleted");
        MvcResult requestResult = mockMvc.perform(delete("/deleteMapping").param("student-id", "1"))
                .andExpect(status().isOk()).andExpect(status().isOk()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "Student is deleted");*/
    }


    @Test
    public void deleteProduct() throws Exception {

        String uri = "/products/2";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Product is deleted successsfully");
    }


    @Test
    public void add_user() throws Exception {
        UserPostDto user1 = new UserPostDto(1L, "SamMJ", "0123", "0124", "Sam00", "Johns00");
         User user2 = new User(1L, "SamMJ", "0123", "0124", "Sam", "Johns");

      /*  Student student = new Student();
        student.setId(1);
        student.setName("Arun");*/



    }

}
