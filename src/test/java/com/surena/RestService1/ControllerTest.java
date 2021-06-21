package com.surena.RestService1;


import com.surena.RestService1.controller.UserController;
import com.surena.RestService1.dto.UserGetDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    }


}
