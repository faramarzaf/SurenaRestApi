package com.surena.RestService1;

import com.surena.RestService1.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestService1ApplicationTests {

    @Autowired
    private UserController userController;

    @Test
    void test_controllers() {
        assertThat(userController).isNotNull();
    }


}
